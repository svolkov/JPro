package day11.filelist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by syv on 04.03.15.
 */
public class SimpleFileList implements SimpleListOnFile{
    private static final int ELEMENTNUMBER_SHIFT = 4;
    private static final int OFFSET_SHIFT = 4;
    private static final int LENGTHELEMENT_SHIFT = 8;
    private static final int NODENUMBER_SHIFT = 12;
    private static final int HEADER_SHIFT = 16;


    File fileOfList;
    RandomAccessFile raf;
    List<NodeDescriptor> nodes;
    List<Integer> nodeIndexes;

    public SimpleFileList(String fileName){
        nodes = new ArrayList<>();
        nodeIndexes = new LinkedList<>();
        this.init(fileName);
    }
    public class NodeDescriptor {
        private int lengthFileBlock;// length of FileBlock (interval of the file to save list element)
        private int offset;// absolute offset of the FileBlock from the begin ot the file in bytes
        private int lengthElement; // length of saved string, as example (0 means empty FileBlock)
        private int nodeNumber; // number of list element within list nodeIndexes (order of elements of the list)

        private NodeDescriptor(){
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLengthFileBlock() {
            return lengthFileBlock;
        }

        public void setLengthFileBlock(int lengthFileBlock) {
            this.lengthFileBlock = lengthFileBlock;
        }

        public int getLengthElement() {
            return lengthElement;
        }

        public void setLengthElement(int lengthElement) {
            this.lengthElement = lengthElement;
        }

        public int getNodeNumber() {
            return nodeNumber;
        }

        public void setNodeNumber(int nodeNumber) {
            this.nodeNumber = nodeNumber;
        }
    }

    public void init(String fileName){
        boolean newfile = false;

        fileOfList = new File(fileName);

        if(!fileOfList.exists())
            try {
                fileOfList.createNewFile();
                newfile = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            raf = new RandomAccessFile(fileOfList,"rw");
            if(newfile){
                raf.seek(0);
                raf.writeInt(0); // number of the list's elements - first 4 bytes
            }else{
                restoreListStructure();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void restoreListStructure(){
        int offset = ELEMENTNUMBER_SHIFT;
        int nodeNumber = 0;
        NodeDescriptor nd;

        initNodeIndexes();

        try {
            while (offset!=raf.length()){

                nd = new NodeDescriptor();
                raf.seek(offset);
                nd.setLengthFileBlock(raf.readInt());

                raf.seek(offset+OFFSET_SHIFT);
                nd.setOffset(raf.readInt());

                raf.seek(offset+LENGTHELEMENT_SHIFT);
                nd.setLengthElement(raf.readInt());

                if (nd.getLengthElement()!=0) {// if fileblock is not empty
                    raf.seek(offset + NODENUMBER_SHIFT);
                    nd.setNodeNumber(raf.readInt());
                    nodeIndexes.set(nd.getNodeNumber(),nodeNumber);
                }
                nodeNumber++;
                nodes.add(nd);
                offset+=HEADER_SHIFT+nd.getLengthFileBlock();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initNodeIndexes(){
        int elementsNumber = 0;

        try {
            raf.seek(0);
            elementsNumber = raf.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
       while (elementsNumber!=0){
         nodeIndexes.add(0);
           elementsNumber--;
       }
    }

    public void add(String str){
        int offset;

        if(nodes.isEmpty()){
            offset = createFirstNodeHeader(str);
        }
        else{
            int index = 0;
            if ((index = findEmptyNode(str.length())) != -1) {
                offset = updateElementLength(index, str.length());
                nodeIndexes.add(index);
            }else {
                offset = createNewNodeHeader(str);
             }
        }
        try {
            raf.seek(offset);
            raf.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateElementsNumber(1);
    }

    private int createNewNodeHeader(String str){
        NodeDescriptor nd = new NodeDescriptor();
        int offset;

        offset = nodes.get(nodes.size() - 1).getOffset() + HEADER_SHIFT + nodes.get(nodes.size() - 1).getLengthFileBlock();

        try {
            nd.setLengthFileBlock(str.length());
            raf.seek(offset);
            raf.writeInt(nd.getLengthFileBlock());

            nd.setOffset(offset);
            raf.seek(offset+OFFSET_SHIFT);
            raf.writeInt(nd.getOffset());

            nd.setLengthElement(str.length());
            raf.seek(offset+LENGTHELEMENT_SHIFT);
            raf.writeInt(nd.getLengthElement());

            nd.setNodeNumber(nodes.size());
            raf.seek(offset+NODENUMBER_SHIFT);
            raf.writeInt(nd.getNodeNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
        nodes.add(nd);
        nodeIndexes.add(nd.getNodeNumber());
        return offset + NODENUMBER_SHIFT +4;
    }

    private int updateElementLength(int index, int length){

        int offset = nodes.get(index).getOffset();
        nodes.get(index).setLengthElement(length);
        try {
            raf.seek(offset+ELEMENTNUMBER_SHIFT);
            raf.write(nodes.get(index).getLengthElement());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return offset;
    }

    private int createFirstNodeHeader(String str){
        NodeDescriptor nd = new NodeDescriptor();

        try {
            nd.setLengthFileBlock(str.length());
            raf.seek(ELEMENTNUMBER_SHIFT);
            raf.writeInt(nd.getLengthFileBlock());

            nd.setOffset(ELEMENTNUMBER_SHIFT);
            raf.seek(ELEMENTNUMBER_SHIFT+OFFSET_SHIFT);
            raf.writeInt(nd.getOffset());

            nd.setLengthElement(str.length());
            raf.seek(ELEMENTNUMBER_SHIFT+LENGTHELEMENT_SHIFT);
            raf.writeInt(nd.getLengthElement());

            nd.setNodeNumber(0);
            raf.seek(ELEMENTNUMBER_SHIFT+NODENUMBER_SHIFT);
            raf.writeInt(nd.getNodeNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
        nodes.add(nd);
        nodeIndexes.add(0);
        return ELEMENTNUMBER_SHIFT + NODENUMBER_SHIFT + 4;
    }

    private int findEmptyNode(int length){
        int resultIndex = -1;

        for (NodeDescriptor nd : nodes){
            if((nd.getLengthElement()==0) && (nd.getLengthFileBlock()>=length)){
                resultIndex = nodes.indexOf(nd);
                break;
            }
        }
        return resultIndex;
    }

    public void close(){
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private void updateElementsNumber(int n){

       try {
           raf.seek(0);
           int tmp = raf.readInt();
           raf.seek(0);
           raf.writeInt(tmp+n);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public void remove(int index){
        int delNodeIndex; // index of the node to be deleted
        int truncatedSize;

        if(index >= nodeIndexes.size()){
            throw new IllegalArgumentException("Index is out of range");
        }
        delNodeIndex = nodeIndexes.get(index);
        try {
            if (delNodeIndex != nodes.size() - 1) {
                nodes.get(delNodeIndex).setLengthElement(0);// marks as empty
                raf.seek(nodes.get(delNodeIndex).getOffset() + LENGTHELEMENT_SHIFT);
                raf.writeInt(0);
            } else {
                truncatedSize = nodes.get(delNodeIndex).getLengthFileBlock() + HEADER_SHIFT;
                nodes.remove(delNodeIndex);
                for (int i = delNodeIndex - 1; i >= 0; i--) {
                    if (nodes.get(i).getLengthElement() == 0) { // if empty
                        truncatedSize += nodes.get(i).getLengthFileBlock() + HEADER_SHIFT;
                        nodes.remove(i);
                    } else {
                        break;
                    }
                }
                raf.setLength(raf.length() - truncatedSize); // File truncation after nodes removal.
            }
        } catch (IOException e) {
              e.printStackTrace();
          }
        nodeIndexes.remove(index);
        updateElementsNumber(-1);  // update ElemenNumber in the File (0-3 bytes)
        updateNodeNumber(index);
    }

    private void updateNodeNumber(int deletedIndex){
        int nodeOffset;

        if(nodeIndexes.size()==deletedIndex || nodeIndexes.isEmpty()){
            return;
        }
        for(int i = deletedIndex; i<nodeIndexes.size();i++){
           nodeOffset = nodes.get(nodeIndexes.get(i)).getOffset();
            try {
                raf.seek(nodeOffset + NODENUMBER_SHIFT);
                raf.writeInt(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void display(){

        if(nodes.isEmpty()){
            System.out.println("SimpleList is empty");
            return;
        }
        for (int index : nodeIndexes){
            if(nodes.get(index).getLengthElement() != 0){ // If FileBlock is empty?
                try {
                    raf.seek(nodes.get(index).getOffset() + HEADER_SHIFT);
                    byte[] bytes = new byte[nodes.get(index).getLengthElement()];
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

    public List <String> getAll(){

        if(nodes.isEmpty()){
            System.out.println("List is empty");
            return null;
        }

        List<String> list = new LinkedList<>();
        for (int index : nodeIndexes){
            if(nodes.get(index).getLengthElement() != 0){ // If FileBlock is empty?
                try {
                    raf.seek(nodes.get(index).getOffset() + HEADER_SHIFT);
                    byte[] bytes = new byte[nodes.get(index).getLengthElement()];
                    raf.readFully(bytes);
                    //System.out.println(new String(bytes));
                    list.add(new String(bytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Error: displayed node is empty");
            }
        }
        //System.out.println();
        return list;
    }
}
