#!/usr/bin/env bash

openssl aes-256-cbc -K $encrypted_99aebaea6bf8_key -iv $encrypted_99aebaea6bf8_iv -in release/codesigning.asc.enc -out release/codesigning.asc -d
gpg --fast-import release/codesigning.asc

mvn versions:set -DnewVersion=$TRAVIS_TAG
mvn deploy -P sign,build-extras,!test-only -DskipTests --settings release/settings.xml
