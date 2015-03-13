Ant task could be obtained by the link: [Download](https://drive.google.com/#folders/0B_YbyHcRFXDnX1JYYzFjcVFmak0)

# Including into ant script #
```xml

<taskdef resource="org/xblackcat/ant/thrift/antlib.xml" classpathref="ant.tasks.classpath"/>
```

# Details #

Simple example:
```
<thriftc executable="D:/FrameWork/thrift-0.9.1/thrift-0.9.1.exe" outputdirbase="${basedir}/src/java.gen">
  <java/>
  <php/>
  <source dir="${basedir}/src/thrift">
    <include name="**/*.thrift"/>
  </source>
  <includes dir="${basedir}/src/includes"/>
</thriftc>
```
Supported all available target languages with al options.

TODO: write down detailed help.