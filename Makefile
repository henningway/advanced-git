install:
	chmod +x ./scripts/install.sh
	./scripts/install.sh

reset:
	bb reset

run:
	bb run

.PHONY: install reset run