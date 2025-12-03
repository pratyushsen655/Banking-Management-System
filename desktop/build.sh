#!/bin/bash
# Build script for Desktop Banking Application

echo "Building Desktop Banking Application..."

# Set source and output directories
SRC_DIR="src/main/java"
OUT_DIR="target/classes"

# Create output directory
mkdir -p $OUT_DIR

# Compile all Java files
javac -d $OUT_DIR -sourcepath $SRC_DIR $(find $SRC_DIR -name "*.java")

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo "To run the application, use: java -cp target/classes com.banking.desktop.ui.LoginFrame"
else
    echo "Build failed!"
    exit 1
fi
