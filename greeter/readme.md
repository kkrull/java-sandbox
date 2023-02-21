# Publishing a library with GitHub Actions

Demonstrates how to publish a Java library to the Maven Central Repository with
GitHub Actions.

## Sources and other local files

- `.github/workflows/java-publish-library.yml`: CI/CD configuration for GitHub
  Actions.
- `.envrc`: direnv configuration, if you want to run any of this locally on your
  own machine.  You copied the template, read the instructions, and filled it in
  with your own data, right?  And don't tell me you checked it into source
  control.  I'm watching you.
- `build.gradle`: Configuration telling Gradle knows how to build, assemble, and
  publish a JAR for the sources in this project.
- `~/.gnupg/`: GNU Privacy Guard keyring(s) containing public/private key pairs
  that are used to sign artifacts, verify signatures, and ruin developer's
  lives.

## Tools and integrations

- [**GitHub Actions**](https://docs.github.com/en/actions): Runs CI/CD workflows
  for this project.
- [**GNU Privacy Guard (aka `gpg`)**](https://gnupg.org/): Signs artifacts.
- [**Maven Central Repository**](https://maven.org/): The place most people
  download their artifacts from, when they use Maven, Gradle, or similar build
  tools.
- [**Sonatype OSSRH**](https://oss.sonatype.org/): Maven repository for open
  source projects.  Artifacts are published here first–internally–before being
  released to the Maven Central Repository.  Your artifacts much pass a series
  of trials before they are allowed to ascend to Maven Central.
- [**Ubuntu keyserver**](https://keyserver.ubuntu.com/): Hosts the author's
  public key, so that others can verify signatures on artifacts they download.
  This allows consumers to be confident that it is indeed the author who is
  pwning them, not some cheap poser.

## Workflow

1. Bump the version number in this project's Gradle configuration.
1. Make sure your GNU Privacy Guard key is unexpired, signs with the primary key
   (not a subkey), and is published to a key server.  Ex-communicate any subkeys
   that are used for signing.  We don't tolerate that kind of behavior around
   here.
1. Update your environment configuration
   1. (local) Update your direnv configuration with Sonatype OSSRH credentials
      and your GNUPG key information.  Copy `.envrc-template` to `.envrc` and
      fill it in.
   1. (remote) Update GitHub Actions secrets (or similar) with Sonatype OSSRH
      credentials and GNUPG key information.
1. Run the CI/CD pipeline on GitHub Actions.  This builds the JAR and publishes
   it to a Nexus Staging Repository on Sonatype OSSRH.
1. Close and release (two separate steps) the staging repository on Sonatype
   Nexus.  It will tell you if information is missing from the POM or if there
   are issues with your keys.
1. Eventually I am told this results in your artifact appearing on the Maven
   Central Repository.  I think that took about a day, when I did this 10 years
   ago.

## Gotchas (with keys–I'm looking at YOU `gpg`)

All the problems you are likely to encounter will be related to artifact
signing.  Most everything else works just fine, but keys?  OMG what a nightmare.

- `gpg` assigns at least 3 different identifiers for everything it creates.
  Passing one kind of ID to a tool that expects a key in one of the other
  formats leads to it saying goodbye to you the not-nice way.
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
  machine's environment manager (or from your CI/CD secrets) to the Gradle
  process, unscathed.  Go ahead.  Skin that smoke wagon and see what happens.
