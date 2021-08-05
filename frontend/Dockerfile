#FROM node:lts-alpine as build-stage
FROM timbru31/node-alpine-git:14 as build-stage
WORKDIR /frontend
COPY package*.json ./

# RUN npm install --save npm-git-install
# RUN npm install --save stomp-websocket
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/homepage.conf /etc/nginx/conf.d/homepage.conf
#--from=build-stage
COPY ./frontend/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]