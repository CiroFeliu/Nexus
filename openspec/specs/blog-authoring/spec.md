# blog-authoring Specification

## Purpose
Define how blog posts are authored in the repository (folder layout, front matter, media references, drafts and translations) and how the build enforces that contract, so authors get a clear failure instead of a broken page.

## Requirements

### Requirement: One folder per post
The system SHALL read each post from `blog/posts/<slug>/index.md` at the repository root, where `<slug>` is the folder name and MUST be lowercase kebab-case (`a-z`, `0-9` and single hyphens between them). Files next to `index.md` in the same folder are the post's media.

#### Scenario: Post folder becomes a slug
- **WHEN** the build finds `blog/posts/shogun-first-steps/index.md`
- **THEN** the post's slug is `shogun-first-steps`

#### Scenario: Invalid slug fails the build
- **WHEN** a post folder is named `Shogun_First_Steps`
- **THEN** the build fails with a message naming the folder and stating the slug rule

#### Scenario: Folder without index.md fails the build
- **WHEN** a folder under `blog/posts/` contains no `index.md`
- **THEN** the build fails with a message naming the folder

#### Scenario: No posts folder
- **WHEN** `blog/posts/` does not exist or contains no post folders
- **THEN** the build succeeds and the blog is generated with no posts

### Requirement: Front matter fields
Each `index.md` SHALL start with a YAML front matter block with `title` (non-empty string), `date` (ISO `YYYY-MM-DD`), `summary` (non-empty string) and `lang` (`en` or `es`), all required, and MAY include `draft` (boolean, default `false`), `translationKey` (non-empty string) and `tags` (list of non-empty strings).

#### Scenario: Minimal valid post
- **WHEN** a post declares only `title`, `date`, `summary` and `lang`
- **THEN** the post is accepted as published, untranslated and untagged

#### Scenario: Missing required field
- **WHEN** a post omits `summary`
- **THEN** the build fails with a message naming the post and the missing field

#### Scenario: Invalid value
- **WHEN** a post declares `date: 03/11/2026` or `lang: fr`
- **THEN** the build fails with a message naming the post, the field and the accepted format or values

#### Scenario: Unknown field
- **WHEN** a post declares a field outside the contract, such as `translationkey`
- **THEN** the build fails with a message naming the post and the unknown field

#### Scenario: Missing front matter
- **WHEN** an `index.md` does not start with a `---` delimited front matter block
- **THEN** the build fails with a message naming the post

### Requirement: Drafts are not published
The system SHALL exclude posts with `draft: true` from every generated page, from the feed and from translation links, and SHALL NOT copy their media into the published output.

#### Scenario: Draft post
- **WHEN** a post declares `draft: true`
- **THEN** it has no page, it is absent from `/blog/` and `/blog/feed.xml`, and no other post links to it as a translation

#### Scenario: Draft is still validated
- **WHEN** a draft post has invalid front matter
- **THEN** the build fails, as it would for a published post

### Requirement: Translations share a key
The system SHALL treat posts with the same `translationKey` as language versions of one another, and MUST fail the build when two posts share both the same `translationKey` and the same `lang`.

#### Scenario: Two language versions
- **WHEN** an English post and a Spanish post both declare `translationKey: shogun-intro`
- **THEN** each one is the other's translation

#### Scenario: Conflicting versions
- **WHEN** two English posts declare `translationKey: shogun-intro`
- **THEN** the build fails with a message naming both posts

### Requirement: Media references are relative and must exist
The system SHALL resolve image and video references that are not absolute URLs against the post's own folder, and MUST fail the build when a referenced file does not exist there or the reference leaves the folder.

#### Scenario: Missing media file
- **WHEN** a post references `![Demo](demo.mp4)` and its folder has no `demo.mp4`
- **THEN** the build fails with a message naming the post and the missing file

#### Scenario: Reference outside the post folder
- **WHEN** a post references `![Logo](../other-post/logo.png)`
- **THEN** the build fails with a message naming the post and the reference

### Requirement: Embedded videos stand alone
The system SHALL require a video or YouTube reference to be the only content of its paragraph, and MUST fail the build otherwise, because a video renders as a block figure.

#### Scenario: Video inline with text
- **WHEN** a paragraph reads `See ![Demo](demo.mp4) for details`
- **THEN** the build fails with a message naming the post and asking for the video on its own line
