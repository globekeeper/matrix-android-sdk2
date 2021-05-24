Please also refer to the Changelog of Element Android: https://github.com/vector-im/element-android/blob/master/CHANGES.md

Changes in Matrix-SDK 1.1.5 (2021-04-15)
===================================================

Imported from Element 1.1.5. (https://github.com/vector-im/element-android/releases/tag/v1.1.5)

Changes in Matrix-SDK 1.1.4 (2021-04-09)
===================================================

Imported from Element 1.1.4. (https://github.com/vector-im/element-android/releases/tag/v1.1.4)

Changes in Matrix-SDK 1.1.1 (2021-03-10)
===================================================

Imported from Element 1.1.1. (https://github.com/vector-im/element-android/releases/tag/v1.1.1)

Changes in Matrix-SDK 1.0.16 (2021-02-08)
===================================================

Imported from Element 1.0.16. (https://github.com/vector-im/element-android/releases/tag/v1.0.16)

Changes in Matrix-SDK 1.0.13 (2020-12-21)
===================================================

Imported from Element 1.0.13. (https://github.com/vector-im/element-android/releases/tag/v1.0.13)

Changes in Matrix-SDK 1.0.12 (2020-12-15)
===================================================

Imported from Element 1.0.12. (https://github.com/vector-im/element-android/releases/tag/v1.0.12)

SDK API changes ⚠️:
 - StateService now exposes suspendable function instead of using MatrixCallback.
 - RawCacheStrategy has been moved and renamed to CacheStrategy
 - FileService: remove useless FileService.DownloadMode

Other important changes:
 - Upgrade Realm dependency to 10.1.2
 - Log HTTP requests and responses in production (level BASIC, i.e. without any private data)

Changes in Matrix-SDK 1.0.11 (2020-11-30)
===================================================

Imported from Element 1.0.11. (https://github.com/vector-im/element-android/releases/tag/v1.0.11)

SDK API changes ⚠️:
 - AccountService and some other services now expose suspendable function instead of using MatrixCallback (vector-im/element-android##2354).
   Note: We will incrementally migrate all the SDK API in a near future (vector-im/element-android#2449)

Other important changes:
 - Upgrade Realm dependency to 10.0.0

Changes in Matrix-SDK 1.0.10 (2020-11-04)
===================================================

Imported from Element 1.0.10. (https://github.com/vector-im/element-android/releases/tag/v1.0.10)

Changes in Matrix-SDK 1.0.9 (2020-10-16)
===================================================

Imported from Element 1.0.9. (https://github.com/vector-im/element-android/releases/tag/v1.0.9)

- Setup CI

Changes in Matrix-SDK 1.0.8
===================================================

This release has not been done.

Changes in Matrix-SDK 1.0.7 (2020-09-23)
===================================================

Imported from Element 1.0.7. (https://github.com/vector-im/element-android/releases/tag/v1.0.7)

Changes in Matrix-SDK 1.0.6 (2020-09-08)
===================================================

Imported from Element 1.0.6.

Changes in Matrix-SDK 1.0.5 (2020-08-21)
===================================================

SDK API changes ⚠️:
- PermalinkFactory is now internal, you should use session.permalinkService() to create permalinks

Changes in Matrix-SDK 0.0.1 (2020-08-14)
===================================================

This is the first release of the Matrix SDK.

This first release has been created from the develop branch of Element Android ([at this commit](https://github.com/vector-im/element-android/commit/5a3894036cb34d00177603e69c5b15431212152d)).
Next releases will be done from master branch of Element Android and the version name will be the same between the SDK and Element Android
