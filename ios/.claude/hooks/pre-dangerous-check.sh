#!/bin/bash
set -e

PROJECT_DIR="${CLAUDE_PROJECT_DIR:-$(pwd)}"
PROJECT_NAME=$(basename "$PROJECT_DIR")

# Get command from tool input (passed via stdin as JSON)
INPUT=$(cat)
COMMAND=$(echo "$INPUT" | jq -r '.tool_input.command // empty' 2>/dev/null)
[ -z "$COMMAND" ] && exit 0

# Check for dangerous patterns

# 1. rm -rf on critical directories
if echo "$COMMAND" | grep -qE "rm\s+-rf?\s+(/|~|\\\$HOME|\.git|$PROJECT_NAME|\.claude)"; then
  echo "BLOCKED: rm -rf on critical directory" >&2
  echo "Command: $COMMAND" >&2
  exit 1
fi

# 2. git push --force to main/master
if echo "$COMMAND" | grep -qE 'git\s+push\s+.*--force.*\s+(main|master)'; then
  echo "BLOCKED: Force push to main/master" >&2
  echo "Command: $COMMAND" >&2
  exit 1
fi

if echo "$COMMAND" | grep -qE 'git\s+push\s+-f\s+.*\s+(main|master)'; then
  echo "BLOCKED: Force push to main/master" >&2
  echo "Command: $COMMAND" >&2
  exit 1
fi

# 3. git reset --hard on main/master
if echo "$COMMAND" | grep -qE 'git\s+reset\s+--hard.*origin/(main|master)'; then
  echo "WARNING: Hard reset to origin/main - this will discard local changes" >&2
fi

# 4. Modifications to sensitive files
if echo "$COMMAND" | grep -qE '(>|>>|tee)\s+.*(\.env|credentials|api_key|secret)'; then
  echo "WARNING: Writing to potentially sensitive file" >&2
fi

# 5. Curl piped to bash
if echo "$COMMAND" | grep -qE 'curl.*\|\s*(ba)?sh'; then
  echo "BLOCKED: Piping curl to shell is dangerous" >&2
  exit 1
fi

# 6. git clean (removes untracked files)
if echo "$COMMAND" | grep -qE 'git\s+clean\s+-[a-z]*[fd]'; then
  echo "WARNING: git clean will remove untracked files permanently" >&2
fi

# 7. git checkout -- . (discards all working changes)
if echo "$COMMAND" | grep -qE 'git\s+checkout\s+--\s+\.'; then
  echo "WARNING: git checkout -- . discards all unstaged changes" >&2
fi

# 8. git restore . (discards all working changes)
if echo "$COMMAND" | grep -qE 'git\s+restore\s+\.$'; then
  echo "WARNING: git restore . discards all unstaged changes" >&2
fi

exit 0
