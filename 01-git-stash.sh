git init 01-git-stash
cd 01-git-stash
echo "hello" > hello.txt
git add hello.txt
git commit -m "initial commit"
echo "bye" > bye.txt
git add bye.txt
git stash
