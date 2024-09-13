FROM node:18-alpine AS build

# Répertoire de travail
WORKDIR /app

# Copiez les fichiers package.json
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier le code source dans le conteneur
COPY . .

# Lancer le build
RUN npm run build

# Nginx pour servir l'app
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

# Démarrer nginx
CMD ["nginx", "-g", "daemon off;"]
