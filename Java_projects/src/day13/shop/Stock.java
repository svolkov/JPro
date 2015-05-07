package day13.shop;

import java.util.*;

public class Stock {

	private Map<Class,Map<String,List<Goods>>> storage;

	public Stock() {
		storage = new HashMap<>();
	}

    public boolean isEmpty(){
		return storage.isEmpty();
	}
	public void add(Goods g) {
	  Class gCls = g.getClass();
	  String gName = g.getName();

	  if(storage.isEmpty()){
        storage.put(gCls,new HashMap<String,List<Goods>>());
		storage.get(gCls).put(gName, new LinkedList<Goods>());
		storage.get(gCls).get(gName).add(g);
	  }else{
		if(storage.containsKey(gCls)) {
			if(!storage.get(gCls).containsKey(gName)) {
				storage.get(gCls).put(gName, new LinkedList<Goods>());
			}
			storage.get(gCls).get(gName).add(g);
		}else{
			storage.put(gCls,new HashMap<String,List<Goods>>());
			storage.get(gCls).put(gName, new LinkedList<Goods>());
			storage.get(gCls).get(gName).add(g);
		}
	  }
	}
// Make the list of all types of goods in the stock
	public List<Goods> getListOfGoods(){
		List<Goods> list = new ArrayList<>();

		for(Map.Entry<Class,Map<String,List<Goods>>> entryStorage : storage.entrySet()){
			Map<String,List<Goods>> mapOfGoodsTypes = entryStorage.getValue();
			for(Map.Entry<String,List<Goods>> entryMapOfGoodsTypes : mapOfGoodsTypes.entrySet()){
				list.add(entryMapOfGoodsTypes.getValue().get(0));
			}
		}
		return list;
	}

	public String getAttribute(Class cls, String goodsName){

		String result = null;
		Goods g = storage.get(cls).get(goodsName).get(0);

		if(g instanceof Book){
         result = ((Book) g).getAuthor();
		}else {
			if(g instanceof Magazine){
				result = ((Magazine) g).getIssueNumber();
			}else{
				System.out.println("Wrong object");
			}
		}
		return result;
	}

	public String getStringPrice(Class cls, String goodsName){
		Goods g=storage.get(cls).get(goodsName).get(0);
		return Integer.toString(g.getPrice());
	}

	public String getStringNumberOfGoods(Class cls, String goodsName) {
		return Integer.toString(storage.get(cls).get(goodsName).size());
	}

	public int getNumberOfGoods(Class cls, String goodsName) {
		return storage.get(cls).get(goodsName).size();
	}

	public int getNumberTypesOnStock() {
		int num=0;
		for(Map.Entry<Class,Map<String,List<Goods>>> entryStorage : storage.entrySet()) {
			Map<String, List<Goods>> mapOfGoodsTypes = entryStorage.getValue();
			num += mapOfGoodsTypes.size();
		}
		return num;
	}

	public boolean remove(Class cls, String goodsName, int quantity) {

		if(quantity <= storage.get(cls).get(goodsName).size()){
			for (int i = 0; i < quantity; i++) {
				storage.get(cls).get(goodsName).remove(storage.get(cls).get(goodsName).size() - 1);
			}
			if(storage.get(cls).get(goodsName).isEmpty()){
				storage.get(cls).remove(goodsName);
			}
			return true;
		}else {
			return false;
		}
	}
}
