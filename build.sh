#!/bin/bash
(
cd jdm
gradle clean build aggregatedJavadocs
)
gradle clean build