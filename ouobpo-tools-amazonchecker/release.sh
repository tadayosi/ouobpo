#!/bin/sh

if [ -z $1 ];
then
	echo "enter a version to release."
	echo "> $0 <version>"
	exit
fi

mvn release:perform -DconnectionUrl=scm:svn:https://ouobpo.googlecode.com/svn/tags/ouobpo-tools-amazonchecker-$1