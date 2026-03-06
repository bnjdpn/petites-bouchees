#!/bin/bash

PROJECT_DIR="${CLAUDE_PROJECT_DIR:-$(pwd)}"
PROJECT_NAME=$(basename "$PROJECT_DIR")

echo "$PROJECT_NAME session initialized"

# Show current branch
if [ -d "$PROJECT_DIR/.git" ]; then
  BRANCH=$(git -C "$PROJECT_DIR" branch --show-current 2>/dev/null)
  if [ -n "$BRANCH" ]; then
    echo "Branch: $BRANCH"
  fi

  # Show last commit
  LAST_COMMIT=$(git -C "$PROJECT_DIR" log -1 --format="%h %s" 2>/dev/null | head -c 60)
  if [ -n "$LAST_COMMIT" ]; then
    echo "Last: $LAST_COMMIT"
  fi

  # Check for uncommitted changes
  CHANGES=$(git -C "$PROJECT_DIR" status --porcelain 2>/dev/null | wc -l | tr -d ' ')
  if [ "$CHANGES" -gt 0 ]; then
    echo "$CHANGES uncommitted file(s) in project"
  fi
fi

# Check CLAUDE.md freshness
if [ -f "$PROJECT_DIR/CLAUDE.md" ]; then
  CLAUDE_MTIME=$(stat -f %m "$PROJECT_DIR/CLAUDE.md" 2>/dev/null || stat -c %Y "$PROJECT_DIR/CLAUDE.md" 2>/dev/null)
  if [ -n "$CLAUDE_MTIME" ]; then
    CLAUDE_AGE=$(( ($(date +%s) - CLAUDE_MTIME) / 86400 ))
    if [ "$CLAUDE_AGE" -gt 7 ]; then
      echo "CLAUDE.md not updated in $CLAUDE_AGE days"
    fi
  fi
fi

# XcodeBuildMCP defaults
if [ -f "$PROJECT_DIR/.xcodebuildmcp/config.yaml" ]; then
  echo "XcodeBuildMCP: PetitesBouchees profile persisted (scheme=PetitesBouchees, sim=iPhone 17 Pro)"
fi

# Auto-detect test directory
TEST_DIR=$(find "$PROJECT_DIR" -maxdepth 1 -type d -name "*Tests" 2>/dev/null | head -1)
if [ -n "$TEST_DIR" ]; then
  TEST_COUNT=$(find "$TEST_DIR" -name "*Tests.swift" 2>/dev/null | wc -l | tr -d ' ')
  if [ "$TEST_COUNT" -gt 0 ]; then
    echo "$TEST_COUNT test file(s) available"
  fi
fi

exit 0
