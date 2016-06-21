
docker stop anymarket_oracle_db || echo "O container não está rodando"
docker rm anymarket_oracle_db || echo "Não existe conteiner configurado"

docker run -d -p 422:22 -p 41521:1521 --name anymarket_oracle_db db1/anymarket_oracle_db