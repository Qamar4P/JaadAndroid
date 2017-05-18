# JaadAndroid
Fork from https://github.com/dant3/jaad and add Android compatibility and support for reading GoPro Tags. Only library for this purpose.

# How to use

### From file:
```
GoProTagsBox tags = GoProUtil.getHilights(new RandomAccessFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GOPR0175.MP4", "r"));
```

### From url:
```
InputStream inputStream = new BufferedInputStream(new URL("http://localhost:6582?BRIDGE&%2FGOPR0175.MP4&GOPR0175.MP4&80898399").openConnection().getInputStream());
GoProTagsBox tags = GoProUtil.getHilights(inputStream);
                    
stringBuilder.append("Count: "+tags.getCount());
if(tags.getHiLights() != null){
    for (long l : tags.getHiLights()) {
        stringBuilder.append("\nHiLight: "+l);
    }
}
```

# License

Public domain.

Everyone can used this library without any permision. Inherited condition may applied!


Hit star if this useful for you.
Thanks 
