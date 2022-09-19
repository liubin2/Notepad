#!/bin/bash

echo 'excute health check shell'

curl https://www.fansmeta.io/api/health/check -o check.json
