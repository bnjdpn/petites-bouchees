#!/bin/bash
# PostToolUse hook: auto-run xcodegen after project.yml edits
set -e

INPUT=$(cat)
TOOL=$(echo "$INPUT" | jq -r '.tool_name // empty' 2>/dev/null)
FILE_PATH=$(echo "$INPUT" | jq -r '.tool_input.file_path // empty' 2>/dev/null)

# Only trigger on Edit/Write to project.yml
case "$TOOL" in
  Edit|Write) ;;
  *) exit 0 ;;
esac

if [ -z "$FILE_PATH" ] || [[ "$FILE_PATH" != *"project.yml" ]]; then
  exit 0
fi

PROJECT_DIR="${CLAUDE_PROJECT_DIR:-$(pwd)}"

if [ -f "$PROJECT_DIR/project.yml" ] && command -v xcodegen &>/dev/null; then
  cd "$PROJECT_DIR"
  xcodegen generate --quiet 2>&1
  echo "xcodegen: project.xcodeproj regenerated after project.yml edit"
fi

exit 0
