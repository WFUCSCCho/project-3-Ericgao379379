#!/bin/bash

sizes=(10 20 50 100 200 300 398)
sorters=(merge quick heap bubble transposition)

for size in "${sizes[@]}"; do
  for sorter in "${sorters[@]}"; do
    echo "Running $sorter with size $size"
    java -cp src Proj3 src/Automobile.csv "$sorter" "$size"
  done
done
