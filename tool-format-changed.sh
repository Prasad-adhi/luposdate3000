#!/bin/bash
./tool-format-sort-imports.sh
/opt/idea-IC-193.5662.53/bin/format.sh $(git diff HEAD  --name-only)
