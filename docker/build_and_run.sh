clear

echo "==================================="
echo "Construindo o banco de dados Oracle"
echo "==================================="

docker stop anymarket_oracle_db || echo "O container não está rodando"
docker rm anymarket_oracle_db || echo "Não existe conteiner configurado"
docker rmi db1/anymarket_oracle_db || echo "Não existe imagem configurada"

docker build -t db1/anymarket_oracle_db -f Dockerfile-anymarket_oracle_db .
docker run -d -p 422:22 -p 41521:1521 --name anymarket_oracle_db db1/anymarket_oracle_db


echo "==================================="
echo "DONE"
echo "==================================="
