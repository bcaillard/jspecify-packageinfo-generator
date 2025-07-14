#!/usr/bin/env bash
set -euo pipefail

CURRENT_TAG="$1"

if [[ ! "$CURRENT_TAG" =~ ^v([0-9]+)\.([0-9]+)\.([0-9]+)$ ]]; then
    echo "Le tag fourni ($CURRENT_TAG) n'est pas au format vX.Y.Z"
    exit 1
fi

MAJOR="${BASH_REMATCH[1]}"
MINOR="${BASH_REMATCH[2]}"
PATCH="${BASH_REMATCH[3]}"

find_tag() {
    PATTERN="$1"
    TAG=$(git tag -l "$PATTERN" | sort -r -V | head -n 1 || true)
    echo "$TAG"
}

PREVIOUS_TAG=""
if (( PATCH > 0 )); then
    PREVIOUS_PATCH=$((PATCH - 1))
    PATTERN="v${MAJOR}.${MINOR}.${PREVIOUS_PATCH}"
    PREVIOUS_TAG=$(find_tag "$PATTERN")
else
    if (( MINOR > 0 )); then
        PREVIOUS_MINOR=$((MINOR - 1))
        PATTERN="v${MAJOR}.${PREVIOUS_MINOR}.*"
        PREVIOUS_TAG=$(find_tag "$PATTERN")
    else
        if (( MAJOR > 0 )); then
            PREVIOUS_MAJOR=$((MAJOR - 1))
            PATTERN="v${PREVIOUS_MAJOR}.*.*"
            PREVIOUS_TAG=$(find_tag "$PATTERN")
        fi
    fi
fi

if [[ -z "$PREVIOUS_TAG" ]]; then
    echo "Aucun tag précédent trouvé, utilisation de 'main' comme référence"
    REF="main"
else
    echo "Tag précédent trouvé: $PREVIOUS_TAG"
    REF="$PREVIOUS_TAG"
fi

echo "previousTag=$PREVIOUS_TAG" >> "$GITHUB_OUTPUT"