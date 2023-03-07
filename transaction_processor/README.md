# Program Description:
This program demonstrations an implementation of an **abstract linked-list**. It replicates the functionality of a 'Transaction workbook' that contains a series of transactions, read in through a .csv file.

This version of the program is configured to read in .csv files downloadable from the Chase Bank online banking platform. However, it employs **SOLID principles** to allow for differently formatted .csv files.

# Demoing the program:
1) Clone the repo with the following command:
```
mkdir transaction_processor_repo
cd transaction_processor_repo
git init
git remote add -f origin git@github.com:briggstwitchell/resume.git

git config core.sparseCheckout true

echo "resume/transaction_processor/" >> .git/info/sparse-checkout

git pull origin master

```

2) type `make` to compile all the Java files.
3) type `java Main` to start execution -- the program will prompt you with instructions on how to continue.
