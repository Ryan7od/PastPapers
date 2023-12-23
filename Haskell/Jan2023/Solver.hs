module Solver where

import Data.List
import Data.Char

import Types
import WordData
import Clues
import Examples

------------------------------------------------------
-- Part I

punctuation :: String
punctuation 
  = "';.,-!?"

cleanUp :: String -> String
cleanUp xs
  = [ toLower x | x <- xs, not (x `elem` punctuation) ]

split2 :: [a] -> [([a], [a])]
split2 xs = [ splitAt x xs | x <- [1..(length xs - 1)] ]

split3 :: [a] -> [([a], [a], [a])]
split3 xs
  = triple split ++ map (\(x,y) -> (x, [], y)) split
    where split = split2 xs
          triple xs 
            = concat [ tripleHelp (split2 x1) x2 | (x1, x2) <- xs]
          tripleHelp xs x2 
            = map (\(x, y) -> (x, y, x2)) xs

uninsert :: [a] -> [([a], [a])]
uninsert xs
  = map (\(x, y, z) -> (y, x ++ z)) $ filter (\(x, y, z) -> length y /= 0) (split3 xs)
-- Filters for non (_, [], _) triples then maps 1 and 3 together

-- Uncomment these functions when you have defined the above.
split2M :: [a] -> [([a], [a])]
split2M xs
  = sxs ++ [(y, x) | (x, y) <- sxs] 
  where
    sxs = split2 xs

split3M :: [a] -> [([a], [a], [a])]
split3M xs
  = sxs ++ [(z, y, x) | (x, y, z) <- sxs]
  where
    sxs = split3 xs


------------------------------------------------------
-- Part II

matches :: String -> ParseTree -> Bool
matches s (Synonym s') 
  = s `elem` (synonyms s')
matches s (Anagram _ s') 
  = (sort s) == (sort s')
matches s (Reversal _ t)
  = matches (reverse s) t
matches s (Insertion _ t1 t2) 
  = length [ 1 | (s1, s2) <- uninsert s, matches s1 t1, matches s2 t2] /= 0
matches s (Charade _ t1 t2)
  = length [ 1 | (s1, s2) <- split2 s, matches s1 t1, matches s2 t2] /= 0
matches s (HiddenWord _ s')
  = length [ 1 | (w1, inWord, w2) <- split3 $ concat $ words s', s == inWord, (length w1) < (length (head (words s'))), (length w2) < (length (last (words s'))) ] /= 0


evaluate :: Parse -> Int -> [String]
evaluate (d, _, t) n
  = [ syn | syn <- synonyms def, syn `matches` t, length syn == n ]
    where def = unwords d

------------------------------------------------------
-- Part III

-- Given...
parseWordplay :: [String] -> [ParseTree]
parseWordplay ws
  = concat [parseSynonym ws,
            parseAnagram ws,
            parseReversal ws,
            parseInsertion ws,
            parseCharade ws,
            parseHiddenWord ws]

parseHiddenWord :: [String] -> [ParseTree]
parseHiddenWord xs
  = [ HiddenWord [unwords x1] (unwords x2) | (x1, x2) <- split2 xs, (unwords x1) `elem` hiddenWordIndicators]
    
parseSynonym :: [String] -> [ParseTree]
parseSynonym xs 
  | synonyms x == [] = []
  | otherwise = [Synonym x]
    where x = unwords xs

parseAnagram :: [String] -> [ParseTree]
parseAnagram xs
  = [ Anagram [unwords x1] (concat x2) | (x1, x2) <- split2M xs, (unwords x1) `elem` anagramIndicators]

parseReversal :: [String] -> [ParseTree]
parseReversal xs
  = concat [ [ Reversal [unwords $ x1] t | t <- parseWordplay x2 ] | (x1, x2) <- split2M xs, (unwords x1) `elem` reversalIndicators]

parseInsertion :: [String] -> [ParseTree]
parseInsertion xs
  = concat $ map func $ split3 xs
    where func :: ([String], [String], [String]) -> [ParseTree]
          func (arg, ws, arg')
            | ind `elem` envelopeIndicators
                = [ Insertion [ind] pa' pa | pa' <- pArg', pa <- pArg ]
            | ind `elem` insertionIndicators 
                = [ Insertion [ind] pa pa' | pa' <- pArg', pa <- pArg ]
            | otherwise = []
              where ind = unwords ws
                    pArg = parseWordplay arg
                    pArg' = parseWordplay arg'

parseCharade :: [String] -> [ParseTree]
parseCharade xs
  = concat $ map func $ split3 xs
    where func :: ([String], [String], [String]) -> [ParseTree]
          func (arg, ws, arg')
            | ind `elem` beforeIndicators
                = [ Charade [ind] pa pa' | pa' <- pArg', pa <- pArg ]
            | ind `elem` afterIndicators 
                = [ Charade [ind] pa' pa | pa' <- pArg', pa <- pArg ]
            | otherwise = []
              where ind = unwords ws
                    pArg = parseWordplay arg
                    pArg' = parseWordplay arg'



-- Given...
parseClue :: Clue -> [Parse]
parseClue clue@(s, n)
  = parseClueText (words (cleanUp s))

parseClueText :: [String] -> [Parse]
parseClueText xs
  = concat [ [ (def, link, pwp) | pwp <- parseWordplay wp ] | (def, link, wp) <- split3M xs, (unwords link) `elem` linkWords, (length $ synonyms (unwords def)) /= 0 ]

solve :: Clue -> [Solution]
solve clue@(s, n)
  = [ (clue, tree, head $ evaluate tree n) | tree <- parseClue clue, length (evaluate tree n) /= 0 ]


------------------------------------------------------
-- Some additional test functions

-- Returns the solution(s) to the first k clues.
-- The nub removes duplicate solutions arising from the
-- charade parsing rule.
solveAll :: Int -> [[String]]
solveAll k
  = map (nub . map getSol . solve . (clues !!)) [0..k-1]

getSol :: Solution -> String
getSol (_, _, sol) = sol

showAll
  = mapM_ (showSolutions . solve . (clues !!)) [0..23]

