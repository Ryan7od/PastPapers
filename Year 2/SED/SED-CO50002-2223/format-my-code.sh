echo "Formatting code..."
find {Q1,Q2} -name "*.java" -exec java -jar lib/google-java-format-1.15.0-all-deps.jar -r {} \;
echo "Done."
