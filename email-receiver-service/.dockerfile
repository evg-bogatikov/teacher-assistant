FROM node:16-alpine
WORKDIR ./node/app
COPY package*.json ./
RUN npm install
COPY . .
ENTRYPOINT ["node", "app.js"]
EXPOSE 8082
