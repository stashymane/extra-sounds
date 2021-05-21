# Changelog

## [1.3.1] - 2021-05-21

### Fixed

- Modded items not having sounds
- Crashing if item does not have a sound assigned

### Changed

- Moved more sounds from hardcoded to class based detection

## [1.3.0] - 2021-05-20

### Added

- Item stack drag sound
- Drop sound pitch based on amount (versus max stack)

### Fixed

- Item transfer sound in creative inventory
- Multiple sounds being played at the same time
- Crash when playing sounds during a sound tick

### Changed

- Moved sound logic to item creation instead of click event

## [1.2.0] - 2021-05-17

### Added

- Hotbar sound on key press
- Item delete sound (in creative inventory)

### Fixed

- Drop sound when disconnecting
- Drop sound playing when dropping nothing
- Crashing, once and for all (hopefully)

## [1.1.1] - 2021-05-10

### Changed

- Removed cloth config from jar
- Set environment to client only

## [1.1.0] - 2021-05-09

### Added

- Block-based inventory sounds
- Creative inventory click sounds

### Fixed

- Fix crashes on certain clicks
- Include cloth config in jar

## [1.0.0] - 2021-05-05

Initial release
