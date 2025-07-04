# Use official Java 17 image
FROM openjdk:17

# Set working directory inside container
WORKDIR /app

# Copy all source files
COPY . .

# Compile all Java classes
RUN mkdir -p classes && \
    find . -name "*.java" -print0 | xargs -0 javac -d classes

# Run a Main class (replace with your actual main class)
CMD ["java", "-cp", "classes", "com.example.bookmyshow.Main"]
