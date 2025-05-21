#!/bin/bash

VERSION="2.51.2"
OS="linux"
ARCH="amd64"

URL="https://github.com/prometheus/prometheus/releases/download/v$VERSION/prometheus-$VERSION.$OS-$ARCH.tar.gz"

mkdir -p prometheus
cd prometheus || exit 1

if [[ ! -f prometheus ]]; then
  echo "🔽 Downloading Prometheus $VERSION..."
  curl -sL "$URL" | tar -xz --strip-components=1 prometheus-$VERSION.$OS-$ARCH/prometheus prometheus-$VERSION.$OS-$ARCH/promtool
  echo "✅ Prometheus downloaded."
else
  echo "✅ Prometheus already present."
fi
