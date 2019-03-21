FROM node:11.7.0-alpine
RUN mkdir app
WORKDIR /app
ADD package*.json /app/
RUN npm install --production
COPY . /app/
RUN npm rebuild node-sass
RUN npm install leaflet react-leaflet
RUN npm install @types/react-leaflet
RUN npm install react-datepicker --save
RUN npm install @types/react-datepicker
CMD [ "npm", "run", "start:dev"]
EXPOSE 3001

