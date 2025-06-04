#!/bin/bash

ES_HOST="localhost"
ES_PORT="9200"
INDEX_NAME="movies"

# Check if Elasticsearch is running
if curl -s "http://$ES_HOST:$ES_PORT" | grep -q "cluster_name"; then
  echo "✅ Elasticsearch is running at $ES_HOST:$ES_PORT"
else
  echo "❌ Elasticsearch is not running at $ES_HOST:$ES_PORT"
  exit 1
fi

# Check if the index exists
if curl -s -o /dev/null -w "%{http_code}" "$ES_HOST:$ES_PORT/$INDEX_NAME" | grep -q "200"; then
  echo "ℹ️ Index '$INDEX_NAME' exists. Deleting..."
  curl -s -X DELETE "$ES_HOST:$ES_PORT/$INDEX_NAME" && echo "✅ Deleted index '$INDEX_NAME'"
else
  echo "ℹ️ Index '$INDEX_NAME' does not exist. No need to delete."
fi

# Create the index
echo "🚀 Creating index '$INDEX_NAME'..."
curl -X PUT "http://localhost:9200/movies" -H "Content-Type: application/json" -d "{\"settings\":{\"number_of_shards\":1,\"number_of_replicas\":1,\"analysis\":{\"analyzer\":{\"autocomplete\":{\"tokenizer\":\"edge_ngram_tokenizer\",\"filter\":[\"lowercase\"]}},\"tokenizer\":{\"edge_ngram_tokenizer\":{\"type\":\"edge_ngram\",\"min_gram\":2,\"max_gram\":20,\"token_chars\":[\"letter\",\"digit\"]}}}},\"mappings\":{\"properties\":{\"id\":{\"type\":\"long\"},\"title\":{\"type\":\"text\",\"analyzer\":\"autocomplete\",\"search_analyzer\":\"standard\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"genre\":{\"type\":\"keyword\"},\"actors\":{\"type\":\"keyword\"},\"description\":{\"type\":\"text\"},\"image\":{\"type\":\"text\",\"index\":false},\"released\":{\"type\":\"text\"},\"rating\":{\"type\":\"float\",\"index\":false},\"suggest\":{\"type\":\"completion\"}}}}" && echo "✅ Created index '$INDEX_NAME'"
