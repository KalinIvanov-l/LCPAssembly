[![CircleCI Status](https://circleci.com/gh/rubocop/rubocop/tree/master.svg?style=svg)](https://app.circleci.com/pipelines/github/KalinIvanov-l/LCPAssembly?branch=main)

<p align="center">
  <img src="https://raw.githubusercontent.com/KalinIvanov-l/lcp-assembly/master/core/src/main/resources/assets/logo.png" alt="lcp-assembly Logo" />
</p>

| Branch                                               | build status                                                                                        |
|------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| [main](https://github.com/KalinIvanov-l/LCPAssembly) | ![Build Status](https://github.com/KalinIvanov-l/LCPAssembly/actions/workflows/maven.yml/badge.svg) |

## About
lcp-assembly is an educational project focused on learning and experimenting with different instructions using Java.

![](https://i.imgur.com/waxVImv.png)

## Getting Started
1. **Prerequisites**:
    - Make sure you have Java installed on your machine. You can download it from [the official website](https://www.java.com/).

2. **Installation**:
    - Clone the repository using the command `git clone https://github.com/KalinIvanov-l/lcp-assembly.git`.
    - Open the project in your preferred Java IDE.

3. **Usage**:
    - Experiment with different instructions and observe the outcomes.

![](https://i.imgur.com/waxVImv.png)

## Running with Docker

We provide a Dockerfile to help you run this project without having to install all the dependencies on your machine.

1. **Build the Docker image**:
    ```bash
    docker build -t lcp-assembly .
    ```

2. **Run the Docker container**:
    ```bash
    docker run -it --name lcp-assembly lcp-assembly
    ```

This will build a Docker image with all the necessary dependencies and run it as a container, allowing you to experiment with the project in an isolated environment.

![](https://i.imgur.com/waxVImv.png)

## Contributing
Feel free to fork the repository, create a new branch for your work, and open a pull request!

![](https://i.imgur.com/waxVImv.png)

## License
This project is licensed under the MIT License—see the [LICENSE](LICENSE) file for more details.
