# Publishing a library with GitHub Actions

Demonstrates how to publish a Java library to the Maven Central Repository with
GitHub Actions.

## Sources and other local files

- `/.github/workflows/java-publish-library.yml`: CI/CD configuration for GitHub
  Actions.
- `build.gradle`: Configuration telling Gradle knows how to build, assemble, and
  publish a JAR for the sources in this project.
- `~/.gnupg/`: GNU Privacy Guard keyring(s) containing public/private key pairs
  that are used to sign artifacts and verify their signature.

## Tools and integrations

- **GitHub Actions**: Runs CI/CD workflows for this project.
- **GNU Privacy Guard (aka `gpg`)**: Signs artifacts.
- **Maven Central Repository**: The place most people download their artifacts
  from, when they use Maven, Gradle, or similar build tools.
- **Sonatype OSSRH**: Maven repository for open source projects.  Artifacts are
  published here first–internally–before being released to the Maven Central
  Repository.
- **Ubuntu key manager**: Hosts the author's public key, so that others can
  verify signatures on artifacts they download.

## Workflow

1. Bump the version number in this project's Gradle configuration.
1. Make sure your GNU Privacy Guard key is unexpired, signs with the primary key
   (not a subkey), and is published to a key server.  Ex-communicate any subkeys
   that are used for signing.  We don't tolerate that kinda behavior around
   here.
1. Update your environment configuration
   1. (local) Update your direnv configuration with Sonatype OSSRH credentials
      and your GNUPG key information.
   1. (remote) Update GitHub Actions secrets (or similar) with Sonatype OSSRH
      credentials and GNUPG key information.
1. Run the CI/CD pipeline on GitHub Actions.  This builds the JAR and publishes
   it to a Nexus Staging Repository on Sonatype OSSRH.
1. Close and release (two separate steps) the staging repository on Sonatype
   Nexus.  It will tell you if information is missing from the POM or if there
   are issues with your keys.

## Gotchas (with keys–I'm looking at YOU, `gpg`)

All the problems you are likely to encounter will be related to artifact
signing.  Most everything else works just fine, but keys?  OMG what a nightmare.

- `gpg` assigns at least 3 different identifiers for everything it creates.
  Passing one kind of ID to a tool that expects a key in one of the other
  formats leads to saying goodbye in Germany, not the much-preferable Austria.
- `gpg` generates an additional "subkey" of the parent key you generate on your
  local machine.  You have to go and delete that subkey so that `gpg` uses just
  the parent key to sign things.  Otherwise Sonatype OSSRH will send you a bill
  for new jockey shorts.
- Keys shall not be expired.  Trying to sign an artifact with an expired key
  leads to much weeping and gnashing of teeth.  Not revoking an expired key also
  leads to much weeping and gnashing of teeth.  Tell the snake oil salesman who
  claims that you can extend a key's life to go pound sand.
- Keys shall be distributed (slowly).  Go ahead and send your key.  You'll wait.
  Seriously, it takes time for Sonatype OSSRH to realize you published your key,
  even though it queries the exact server you just uploaded your public key to.
  Send your key and make a sandwich.  No sense in debugging this stuff on an
  empty stomach.
- Good luck encoding your newline-happy, ASCII-armored, private key as an
  environment variable in your shell.  It is possible, but only by knowing
  BASH's darkest secrets.  And WTF is ASCII armor anyway?  Is it better than
  mithril?
- Good luck passing said environment variables all the way from your local
  machine's environment manager (or from your CI/CD secrets) all the Gradle
  process, unscathed.  Skin that smokewagon and see what happens.  Or are you
  just gonna stand there and bleed?
