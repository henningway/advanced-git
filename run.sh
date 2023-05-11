#!/bin/bash

options=("git-stash" "tbd" "tbd")

menu() {
  echo "Available problems:"
  for i in ${!options[@]}; do
    printf "%3d) %s\n" $((i+1)) "${options[i]}"
  done
  if [[ "$msg" ]]; then echo "$msg"; fi
}

prompt="Pick an option: "

menu

read -rp "$prompt" num

if [[ $num != *[[:digit:]]* ]] || (( num < 1 || num > ${#options[@]} )); then
  echo "Invalid option: $num"
  exit
fi

problem="0$num-${options[num-1]}"

script="$problem.sh"

echo "Running $script"

source $script
$SHELL
