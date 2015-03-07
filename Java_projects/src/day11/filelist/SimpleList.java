package day11.filelist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by syv on 01.03.15.
 */
public class SimpleList implements SimpleListOnFile{
    Path pathFileList;
    RandomAccessFile raf;
    List <NodeDescriptor> nodes;
    List<Integer> nodeIndexes;

    public class NodeDescriptor {
        private int offset;
        private int length;
        private boolean empty;

        private NodeDescriptor(){
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }
    }
    public SimpleList(){
        nodes = new ArrayList<>();
        nodeIndexes = new LinkedList<>();
        this.init();
    }

    public void init(){
        try {
            pathFileList = Files.createTempFile("simplelist",null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //pathFileList.toFile().deleteOnExit();
        try {
            raf = new RandomAccessFile(pathFileList.toFile(),"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String str){
        int offset;
        NodeDescriptor nd;

        if(nodes.isEmpty()){
            nd = new NodeDescriptor();
            nd.setLength(str.length());
            nd.setEmpty(false);
            nd.setOffset(0);
            nodes.add(nd);
            offset = 0;
            nodeIndexes.add(0);
            }
       else{
         int index = 0;
         if ((index = findEmptyNode(str.length())) != -1) {
            nodes.get(index).setLength(str.length());
            nodes.get(index).setEmpty(false);
            offset = nodes.get(index).getOffset();
            nodeIndexes.add(index);

         } else {
            nd = new NodeDescriptor();
            nd.setLength(str.length());
            nd.setEmpty(false);
            nd.setOffset(nodes.get(nodes.size() - 1).getOffset()+str.length());
            nodes.add(nd);
            offset = nd.getOffset();
            nodeIndexes.add(nodes.size()-1);
         }
       }
       try {
            raf.seek(offset);
            raf.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(){
        byte[] bytes = new byte[3];

        if(nodes.isEmpty()){
            System.out.println("SimpleList is empty");
            return;
        }
        for (int index : nodeIndexes){
            if(!nodes.get(index).isEmpty()){
                try {
                    raf.seek(nodes.get(index).getOffset());
                    raf.readFully(bytes);
                    System.out.println(new String(bytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Error: displayed node is empty");
            }
        }
        System.out.println();
    }

    @Override
    public List<String> getAll() {
        return null;
    }

    public void remove(int index){
       int delIndex;
       //int truncatedSize;

        if(index >= nodeIndexes.size()){
          throw new IllegalArgumentException("Index is out of range");
        }

        delIndex = nodeIndexes.get(index);

        if(delIndex != nodes.size()-1){
            nodes.get(delIndex).setEmpty(true);
        }else{
           // truncatedSize = nodes.get(delIndex).getLength();
            nodes.remove(delIndex);
            for (int i = delIndex-1;i>=0;i--){
                if(nodes.get(i).isEmpty()){
             //     truncatedSize += nodes.get(i).getLength();
                  nodes.remove(i);
                }else{
                    break;
                }
            }
        }
        nodeIndexes.remove(index);

    }

    private int findEmptyNode(int length){
       int resultIndex = -1;

       for (NodeDescriptor nd : nodes){
           if(nd.isEmpty() && (nd.getLength()>=length)){
               resultIndex = nodes.indexOf(nd);
               break;
           }
       }
       return resultIndex;
    }
}
