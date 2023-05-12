install:
	chmod +x ./scripts/install.sh
	./scripts/install.sh

run:
	bb --classpath src -m agit.core;

.PHONY: install run