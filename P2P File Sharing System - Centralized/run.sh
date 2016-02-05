#!/bin/bash
cd execution/

echo 'Enter 1 to Start Server or 2 to start Client'
read choice
if [ $choice == '1' ];  then
	java -jar ServerFinal.jar
else
	java -jar ClientFinal.jar
fi