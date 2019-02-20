FROM node:11.7.0-alpine
RUN mkdir app
WORKDIR /app
COPY . /app/
RUN npm install --production
CMD [ "npm", "start" ]
EXPOSE 3001

