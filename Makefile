install:
	chmod +x ./scripts/install.sh
	./scripts/install.sh

run:
	bb src/core.clj;

.PHONY: install run