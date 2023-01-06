
# ModelEngineMerger

ModelEngineMerger is a simple lightweight plugin to save time of the server owners. It merges the auto-generated resource pack of the Model Engine to ItemsAdder in nanoseconds.




## Commands

| Command | Description 
| :-------- | :------------------------- |
| `/mem reload` | Reloads the plugin and the config. |

| Command | Description 
| :-------- | :------------------------- |
| `/mem merge` | Merges the ME resource pack folder with ItemsAdder's resource pack folder. |


## Config

You can change the config in case of different folder names.

```yml
  paths:
    from: "ModelEngine/resource pack" # ModelEngine's resource pack folder path
    to: "ItemsAdder/contents/modelengine/resourcepack" # ItemsAdder's resource pack file
```
