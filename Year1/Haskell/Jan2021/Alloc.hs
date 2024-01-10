module Alloc where

import Data.Maybe
import Data.List

import Types
import Examples

------------------------------------------------------
--
-- Part I
--
count :: Eq a => a -> [a] -> Int
count e xs
  = foldl (\acc i -> if i == e then acc + 1 else acc) (0) xs

degrees :: Eq a => Graph a -> [(a, Int)]
degrees ([], _) = []
degrees g@((n:ns), es)
  = (n, length $ neighbours n g): degrees (ns, es)

neighbours :: Eq a => a -> Graph a -> [a]
neighbours e (_, es)
  = [ y | (x, y) <- es, x == e ] ++ [ x | (x, y) <- es, y == e ]

removeNode :: Eq a => a -> Graph a -> Graph a
removeNode x (ns, es)
  = (filter (/= x) ns, filter (\(e, e') -> x /= e && x /= e') es)

------------------------------------------------------
--
-- Part II
--
colourGraph :: (Ord a, Show a) => Int -> Graph a -> Colouring a
colourGraph _ ([], _) = []
colourGraph n g
  = (low, lowF ([1..n] \\ takenCols)) : col
    where low = fst $ foldr (\acc e@(i, x) -> if x < snd acc then e else acc) (head $ degrees g) (degrees g)
          !col = colourGraph n (removeNode low g)
          takenCols = [ y | (x, y) <- col, x `elem` (neighbours low g) ]
          lowF [] = 0
          lowF xs = head xs

------------------------------------------------------
--
-- Part III
--
buildIdMap :: Colouring Id -> IdMap
buildIdMap xs
  = ("return", "return") : map (\(x, n) -> if n == 0 then (x, x) else (x, "R" ++ show n)) xs

buildArgAssignments :: [Id] -> IdMap -> [Statement]
buildArgAssignments xs maps
  = map (\e -> Assign (lookUp e maps) (Var e)) xs

renameExp :: Exp -> IdMap -> Exp
-- Pre: A precondition is that every variable referenced in 
-- the expression is in the idMap. 
renameExp x@(Const x') _ 
  = x 
renameExp (Var x) im 
  = Var (lookUp x im)
renameExp (Apply op e e') im 
  = Apply op (renameExp e im) (renameExp e' im)


renameBlock :: Block -> IdMap -> Block
-- Pre: A precondition is that every variable referenced in 
-- the block is in the idMap. 
renameBlock xs im = filter (sameAssignC) (map (renameBlock' im) xs)
  where sameAssignC (Assign x (Var y))
          = if x == y then False else True
        sameAssignC _ = True

renameBlock' :: IdMap -> Statement -> Statement
renameBlock' im (Assign x y)
  = Assign (lookUp x im) (renameExp y im)
renameBlock' im (If e b b')
  = If (renameExp e im) (renameBlock b im) (renameBlock b' im)
renameBlock' im (While e b)
  = While (renameExp e im) (renameBlock b im)

renameFun :: Function -> IdMap -> Function
renameFun (f, as, b) idMap
  = (f, as, buildArgAssignments as idMap ++ renameBlock b idMap)

-----------------------------------------------------
--
-- Part IV
--
buildIG :: [[Id]] -> IG
buildIG xs 
  = (totalVars, nubbedEdges [] allEdges)
    where totalVars = nub $ concat xs
          useful = (filter (\x -> length x > 1) xs)
          allEdges = concatMap (\(y:ys) -> [(y, y') | y' <- ys]) useful
          nubbedEdges xs [] = xs
          nubbedEdges xs (y:ys)
            | y `elem` xs || (invert y) `elem` xs = nubbedEdges xs ys
            | otherwise = nubbedEdges (y:xs) ys
            where invert (y1, y2) = (y2, y1)
  

-----------------------------------------------------
--
-- Part V
--
liveVars :: CFG -> [[Id]]
liveVars cfg
  = map (liveVars' cfg (length cfg)) cfg

liveVars' :: CFG -> Int -> ((Id, [Id]), [Int]) -> [Id]
liveVars' _ 0 _ = []
liveVars' cfg n ((def, use), succ) 
  = union use ((liveOfSucc succ) \\ [def])
    where liveOfSucc s = nub $ concat [ liveVars' cfg (n-1) (cfg !! x) | x <- s ]

buildCFG :: Function -> CFG
buildCFG (f, as, b )= fst $ buildCFG' 0 b

buildCFG' :: Int -> Block -> (CFG, Int)
buildCFG' n [] = ([], n)
buildCFG' n (Assign x (Const y) : bs)
  | x == "return" = (((x, []), []) : (fst lbuild), snd lbuild)
  | otherwise = (((x, []), [n+1]) : (fst lbuild), snd lbuild)
  where !lbuild = buildCFG' (n+1) bs
buildCFG' n (Assign x e : bs)
  | x == "return" = (((x, varsIn e), []) : (fst lbuild), snd lbuild)
  | otherwise = (((x, varsIn e), [n+1]) : (fst lbuild), snd lbuild)
  where !lbuild = buildCFG' (n+1) bs
buildCFG' n (If e b1 b2 : bs)
  = ([(("_", varsIn e), [n+1, snd build1])] ++ fst build1 ++ fst build2 ++ (fst lbuild), snd lbuild)
    where !build1 = buildCFG' (n+1) b1
          !build2 = buildCFG' (snd build1) b2
          next = snd build2
          !lbuild = buildCFG' next bs
buildCFG' n (While e b : bs)
  = ([(("_", varsIn e), [n+1, snd build])] ++ fst build ++ (fst lbuild), snd lbuild)
    where !build = buildCFG' (n+1) b
          !lbuild = buildCFG' (snd build) bs

varsIn :: Exp -> [Id]
varsIn (Const _) = []
varsIn (Var x) = [x]
varsIn (Apply _ e e') = varsIn e ++ varsIn e'