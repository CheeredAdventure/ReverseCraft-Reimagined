# Contributing to ReverseCraft Reimagined

Thank you for your interest in contributing to this project! Please follow these guidelines to ensure a smooth contribution process.

---

## Required Tools

To contribute to this project, you will need the following tools:

- **Java Development Kit (JDK)**: Version 17 is required. recommended to use `Eclipse Adoptium` for minecraft modding, but totally up to you.
  - [Eclipse Adoptium](https://adoptium.net/) (Recommended)
  - [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) (Not recommended for modding)
- **Gradle**: Used for building the project and managing dependencies. you can use the gradle wrapper provided in this project, and this is the way recommended by gradle.
- **Integrated Development Environment (IDE)**: We, `CheeredAdventure`, recommend using one of the following IDEs for development.
  - [Visual Studio Code](https://code.visualstudio.com/) (Recommended extension: Java Extension Pack)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community Edition is sufficient)

---

## Setting Up the Development Environment

1. **Fork the Repository**  
   Fork this repository on GitHub and clone it locally:
   ```bash
   git clone https://github.com/<your-username>/ReverseCraft-Reimagined.git
   cd ReverseCraft-Reimagined
   ```

2. **Install Dependencies**  
   Use Gradle to install dependencies:
   ```bash
   ./gradlew genIntellijRuns # for IntelliJ users
   ./gradlew genVSCodeRuns # for Visual Studio Code users
   ./gradlew build
   ```

3. **Open the Project in Your IDE**  
   - **Visual Studio Code**: Use `File > Open Folder` to open the project folder.
   - **IntelliJ IDEA**: Use `File > Open` to open the project folder and import it as a Gradle project.

4. **Test in Minecraft Environment**  
   Launch Minecraft in the development environment to verify functionality:

   Currently, verifying the mod in the server environment is not required.

---

## Coding Standards

- **Code Style**:  
  - Follow [Google's coding style](https://google.github.io/styleguide/jsguide.html) for Java code.
  - Adjust your IDE's formatter settings to match the project's style if necessary.

> Note: line length setting is optional in this project. project master enforces particular line
> length specified by coding style,
> but it is not required for contributors.

- **Comments**:  
  - Use Javadoc comments for classes and methods.
  - Add inline comments for complex logic where appropriate.

- **Naming Conventions**:  
  - Class names: `PascalCase`
  - Method/Variable names: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
    - **Exception**: `org.slf4j.Logger` instance is usually a `private static final` constant. but we treat it as normal static field, thus it is `lowerCamelCase`.

There is no particular rules for naming variables. Your variables should be named in a way that
makes sense to you and your code!

---

## Adding New Features

1. **Check Existing Issues**  
   Before adding a new feature, check the [Issues](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/issues) section to ensure a similar proposal doesn't already exist.

2. **Create a New Branch**  
   Create a branch for your feature:
   ```bash
   git checkout -b feature/<your-feature-name>
   ```

3. **Implement the Code**  
   Add the necessary code and verify its functionality.

4. **Add Tests**  
   Write test cases for the new feature.

5. **Commit Your Changes**  
   Use meaningful commit messages:
   ```bash
   git commit -m "Add <your-feature-name>"
   ```

6. **Create a Pull Request**  
   Submit a pull request on GitHub and describe your changes.

---

## Fixing Bugs

1. **Check Existing Issues**:
   Verify if the bug has already been reported in the [Issues](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/issues) section.

2. **Create a New Branch**:
   Create a branch for the bug fix:
   ```bash
   git checkout -b bugfix/<issue-id>
   ```

3. **Implement the fix**:
   Fix the bug and verify the changes.

4. **Update or Add Tests**:
   Add or update test cases to cover the bug fix.

5. **Commit Your Changes**:
   Use meaningful commit messages:
   ```bash
   git commit -m "Fix <issue-id>: <description>"
   ```

6. **Create a Pull Request**:
   Submit a pull request on GitHub and describe the fix.

---

## Updating Documentation

The documentation in this project is completely optional. Only you have to do is to describe what you have done in the pull request.

But, if you want, you can write the documentation/comments in the code by javadoc style. Thank you for your extra effort!

---

## Code Review

Once you submit a pull request, the project maintainers will review your code.

They may request you to make changes or improvements before merging your pull request. if you don't want to do that, explain your reason in the pull request comment.

---

## License

By contributing to this project, you agree that your contributions will be licensed under the [GNU AGPLv3](./LICENSE).

Your contributions will be publicly available as a member of the `CheeredAdventure` organization,
and you will be listed on the contributors list as a special partner of our organization.

If you do not wish to be listed as a contributor, please let us know in advance or **CLEARLY** indicate in the initial description of your pull request that you wish to be anonymized.

---

## Contact

You can ask some small questions or talk on [Discussions](https://github.com/CheeredAdventure/ReverseCraft-Reimagined/discussions) section.

Project author: @hizumiaoba (a.k.a. `iDOL_Ranfa_M@STER`)

---

That's it! We look forward to your contributions!
