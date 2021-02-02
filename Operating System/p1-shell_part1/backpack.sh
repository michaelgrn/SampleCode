#!/bin/bash
if [ "$1" = "" ];then
  echo "usage: $0 <output file>"
  echo "   output file - the file to save output in"
  exit 0;
fi
dest="$1"

#clean out any files
git clean -f -d
git reset --hard HEAD
git pull --rebase

#Make sure we are in a good state
make clean

make
if [ ! $? -eq 0 ];then
    echo "FAIL: Project failed to compile"  >> $dest
    exit 1
fi

#Make sure that there is an executable named mydash at top level
if [ ! -x "mydash" ];then
    echo "FAIL: No exe named mydash at top level can't test" >> $dest
    exit 1
fi


TESTFILES=smoketest-files

#NORMAL Command
cat $TESTFILES/test-normal-command | ./mydash | grep version.c > /dev/null 2>&1
if test "$?" = "1"
then
    echo "FAIL1: Commands with arguments do not work" >> $dest
else
    echo "PASS1: Commands with arguments" >> $dest
fi

#Readline
grep -r --include=\*.{c,h} "readline.h"  > /dev/null 2>&1
if test "$?" = "1"
then
    echo "FAIL2: Did not find readline.h being used" >> $dest
else
    echo "PASS2: readline.h" >> $dest
fi


#Empty command
cat $TESTFILES/test-empty | ./mydash
if [ ! $? -eq 0 ];then
    echo "FAIL3: empty command" >> $dest
else
    echo "PASS3: empty command" >> $dest
fi


#EOF
cat $TESTFILES/test-eof | ./mydash
if [ ! $? -eq 0 ];then
    echo "FAIL4: EOF" >> $dest
else
    echo "PASS4: EOF" >> $dest
fi

#Exit
cat $TESTFILES/test-exit | ./mydash
if [ ! $? -eq 0 ];then
    echo "FAIL5: exit command" >> $dest
else
    echo "PASS5: exit" >> $dest
fi


#CD
cat $TESTFILES/test-cd | ./mydash
if [ ! $? -eq 0 ];then
    echo "FAIL6: exit return code from mydash cd command" >> $dest
else
    echo "PASS6: return code cd command" >> $dest
fi
if [ ! -e ../"___CD-TEST___" ];then
    echo "FAIL7: Tried to create a file named ___CD-TEST___ and we don't know where it went!" >> $dest
else
    rm ../"___CD-TEST___"
    echo "PASS7: cd test" >> $dest
fi


#Version
./mydash -v >> $dest 2>&1
ps | grep mydash
if [ $? -eq 0 ];then
    echo "FAIL8: mydash did not quit after processing -v command line argument " >> $dest
    killall -9 mydash
else
    echo "PASS8: version" >> $dest
fi

echo "8 Tests complete" >> $dest
