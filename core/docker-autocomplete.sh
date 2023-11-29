#!/bin/bash

if [ -f /etc/bash_completion.d/docker ]; then
    source /etc/bash_completion.d/docker
elif [ -f /usr/share/bash-completion/completions/docker ]; then
    source /usr/share/bash-completion/completions/docker
fi

echo "alias d='docker'" >> ~/.bashrc

# Add autocompletion for 'd'
echo 'complete -F _docker d' >> ~/.bashrc

# shellcheck disable=SC1090
source ~/.bashrc
