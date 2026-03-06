#!/bin/bash
set -e

INPUT=$(cat)
FILE=$(echo "$INPUT" | jq -r '.tool_input.file_path // empty' 2>/dev/null)
[ -z "$FILE" ] && exit 0
[[ "$FILE" != *.swift ]] && exit 0
[ ! -f "$FILE" ] && exit 0
command -v swiftlint &>/dev/null || exit 0

WARNINGS=$(swiftlint lint --quiet --path "$FILE" 2>/dev/null | head -5)
if [ -n "$WARNINGS" ]; then
  echo "SwiftLint:" >&2
  echo "$WARNINGS" >&2
fi
exit 0
