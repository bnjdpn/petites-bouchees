#!/bin/bash
set -e

INPUT=$(cat)
FILE=$(echo "$INPUT" | jq -r '.tool_input.file_path // empty' 2>/dev/null)
[ -z "$FILE" ] && exit 0

case "$FILE" in
  *.mobileprovision|*.p12|*.pem|*.cer)
    echo "BLOCKED: Cannot edit signing file: $FILE" >&2
    exit 1
    ;;
  *asc_api_key*)
    echo "BLOCKED: Cannot edit API key file: $FILE" >&2
    exit 1
    ;;
  *.xcodeproj/*)
    echo "BLOCKED: Edit project.yml instead and run xcodegen generate" >&2
    exit 1
    ;;
  *ExportOptions.plist)
    echo "WARNING: Editing export options — verify signing settings" >&2
    ;;
esac

exit 0
