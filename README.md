# ModelEngineMerger

ModelEngineMerger is a simple lightweight plugin to save time for server owners. It merges the auto-generated resource pack of the Model Engine with ItemsAdder in nanoseconds.

## Commands

| Command | Description |
| :------ | :---------- |
| `/mem reload` | Reloads the plugin and the config. |
| `/mem merge` | Merges the ME resource pack folder with ItemsAdder's resource pack folder. |

## Config

You can change the config in case of different folder names. The new configuration format allows for multiple "from" and "to" folder pairs.

```yml
paths:
  - from: "ModelEngine/resource pack" # ModelEngine's resource pack folder path
    to: "ItemsAdder/contents/modelengine/resourcepack" # ItemsAdder's resource pack file
  - from: "AnotherFolder/resource pack" # Another resource pack folder path
    to: "ItemsAdder/contents/another/resourcepack" # Another destination folder
