{ pkgs }: {
	deps = [
		pkgs.diff
  pkgs.clang_12
		pkgs.ccls
		pkgs.gdb
		pkgs.gnumake
	];
}