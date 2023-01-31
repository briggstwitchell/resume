.PHONY: data

# Create the first chart in ISLR2 Figure 1.1
q1:
	python -B src/q1.py
q2:
	python -B src/q2.py
q3:
	python -B src/q3.py

# Download the data
# mkdir -p fails quietly if directory already exists
# curl -L follows indirects
# curl -O preserves filename of source
data:
	mkdir -p data
	cd data; curl -LO https://ncaaorg.s3.amazonaws.com/research/academics/2020RES_APR2019PubDataShare.csv  
clean:
	rm data/2020RES_APR2019PubDataShare.csv
