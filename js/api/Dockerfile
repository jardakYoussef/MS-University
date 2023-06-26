# Use the official image as a parent image
FROM node:14.17.0-alpine3.13

# Set the working directory
WORKDIR /app

# Copy the package.json and package-lock.json files to the container
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the rest of the application code to the container
COPY . .

# Expose the port the application will be running on
EXPOSE 3000

# Start the application
CMD ["npm", "start"]
