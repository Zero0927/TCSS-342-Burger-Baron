//Siyuan Zhou

public class Burger {
	public final static String classPrefix = "";
	private MyStack<Object> burger; // record ingredients of this burger
	private int numberPatty;// record number of patty of this burger

	/**
	 * a constructor that initializes a Burger with only a bun and patty if
	 * theWorks is false and a Baron Burger if theWorks is true
	 * 
	 * @param theWorks
	 *            if true,initialize a Baron Burger. Or initialize a simple
	 *            Burger
	 */
	public Burger(boolean theWorks) {
		burger = new MyStack<Object>();
		numberPatty = 1;
		if (!theWorks) {
			burger.push(new Bun());
			burger.push(new Patties());
			burger.push(new Bun());
		} else {
			burger.push(new Bun());
			burger.push(new Ketchup());
			burger.push(new Mustard());
			burger.push(new Mushrooms());
			burger.push(new Patties());
			burger.push(new Cheddar());
			burger.push(new Mozzarella());
			burger.push(new Pepperjack());
			burger.push(new Onions());
			burger.push(new Tomato());
			burger.push(new Lettuce());
			burger.push(new BaronSauce());
			burger.push(new Mayonnaise());
			burger.push(new Bun());
			burger.push(new Pickle());
		}
	}

	/**
	 * this method converts the Burger to a String for display.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		MyStack<Object> temp = new MyStack<Object>();
		String result = "[ ";

		while (!burger.isEmpty()) {
			Object ingredient = burger.pop();
			if (ingredient instanceof BaronSauce) {
				result = result + "Baron-Sauce" + ", ";
			} else {
				result = result + ingredient.getClass().getSimpleName() + ", ";
			}
			temp.push(ingredient);
		}
		while (!temp.isEmpty()) {
			burger.push(temp.pop());
		}
		result = result.substring(0, result.length() - 2);
		return result + "]";
	}

	/**
	 * this method changes all patties in the Burger to the pattyType.
	 * 
	 * @param pattyType
	 */
	public void changePatties(String pattyType) {
		MyStack<Object> temp = new MyStack<Object>();
		while (!burger.isEmpty()) {
			Object ingredient = burger.pop();
			if (ingredient instanceof Patties) {
				try {
					ingredient = Class.forName(Burger.classPrefix + pattyType)
							.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			temp.push(ingredient);
		}
		while (!temp.isEmpty()) {
			burger.push(temp.pop());
		}
	}

	/**
	 * this method adds one patty to the Burger up to the maximum of 3.
	 */
	public void addPatty() {
		if (this.numberPatty >= 3)
			return;

		this.numberPatty++;
		MyStack<Object> temp = new MyStack<Object>();
		while (!burger.isEmpty()) {

			Object ingredient = burger.pop();
			if ((ingredient instanceof Cheese)
					|| (ingredient instanceof Patties)) {
				temp.push(new Patties());
				temp.push(ingredient);
				break;
			}
			temp.push(ingredient);
		}

		while (!temp.isEmpty())
			this.burger.push(temp.pop());
	}

	/**
	 * this method removes one patty from the Burger down to the minimum of 1.
	 */
	public void removePatty() {
		if (this.numberPatty <= 1)
			return;

		this.numberPatty--;
		MyStack<Object> temp = new MyStack<Object>();
		while (!burger.isEmpty()) {

			Object ingredient = burger.pop();
			if (ingredient instanceof Patties) {
				break;
			}
			temp.push(ingredient);
		}

		while (!temp.isEmpty())
			this.burger.push(temp.pop());
	}

	/**
	 * this method adds all items of the type to the Burger in the proper
	 * locations.
	 * 
	 * @param type
	 */
	public void addCategory(String type) {
		try {

			Object category = Class.forName(Burger.classPrefix + type)
					.newInstance();

			if (category instanceof Patties) {
				addPatty();
			} else if (category instanceof Veggies) {
				addIngredient("Lettuce");
				addIngredient("Tomato");
				addIngredient("Onions");
				addIngredient("Pickle");
				addIngredient("Mushrooms");
			} else if (category instanceof Cheese) {
				addIngredient("Cheddar");
				addIngredient("Mozzarella");
				addIngredient("Pepperjack");
			} else if (category instanceof Sauces) {
				addIngredient("Ketchup");
				addIngredient("Mustard");
				addIngredient("Mayonnaise");
				addIngredient("BaronSauce");
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method removes all items of the type from the Burger.
	 * 
	 * @param type
	 */
	public void removeCategory(String type) {
		try {
			MyStack<Object> temp = new MyStack<Object>();
			Object category = Class.forName(Burger.classPrefix + type)
					.newInstance();

			if (category instanceof Patties) {

				while (this.numberPatty > 1)
					removePatty();

			} else if (category instanceof Veggies) {
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof Veggies) {
						continue;
					}
					temp.push(ingredient);
				}
			} else if (category instanceof Cheese) {
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof Cheese) {
						continue;
					}
					temp.push(ingredient);
				}
			} else if (category instanceof Sauces) {
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof Sauces) {
						continue;
					}
					temp.push(ingredient);
				}
			}

			while (!temp.isEmpty())
				burger.push(temp.pop());

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method adds the ingredient type to the Burger in the proper
	 * location.
	 * 
	 * @param type
	 */
	public void addIngredient(String type) {
		try {
			Object category = Class.forName(Burger.classPrefix + type)
					.newInstance();

			MyStack<Object> temp = new MyStack<Object>();
			if (category instanceof Pickle) {

				burger.push(new Pickle());

			} else if (category instanceof Mayonnaise) {

				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof Mayonnaise
							|| ingredient instanceof BaronSauce
							|| ((ingredient instanceof Veggies) && !(ingredient instanceof Pickle))
							|| ingredient instanceof Cheese
							|| ingredient instanceof Patties) {
						temp.push(new Mayonnaise());
						temp.push(ingredient);
						break;
					}
					temp.push(ingredient);
				}

			} else if (category instanceof BaronSauce) {

				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof BaronSauce
							|| ((ingredient instanceof Veggies) && !(ingredient instanceof Pickle))
							|| ingredient instanceof Cheese
							|| ingredient instanceof Patties) {
						temp.push(new BaronSauce());
						temp.push(ingredient);
						break;
					}
					temp.push(ingredient);
				}

			} else if (category instanceof Lettuce) {

				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof Lettuce
							|| ingredient instanceof Tomato
							|| ingredient instanceof Onions
							|| ingredient instanceof Pepperjack
							|| ingredient instanceof Mozzarella
							|| ingredient instanceof Cheddar
							|| ingredient instanceof Patties) {
						temp.push(new Lettuce());
						temp.push(ingredient);
						break;
					}
					temp.push(ingredient);
				}

			} else if (category instanceof Tomato) {

				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof Tomato
							|| ingredient instanceof Onions
							|| ingredient instanceof Pepperjack
							|| ingredient instanceof Mozzarella
							|| ingredient instanceof Cheddar
							|| ingredient instanceof Patties) {
						temp.push(new Tomato());
						temp.push(ingredient);
						break;
					}
					temp.push(ingredient);
				}

			} else if (category instanceof Onions) {

				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (ingredient instanceof Onions
							|| ingredient instanceof Pepperjack
							|| ingredient instanceof Mozzarella
							|| ingredient instanceof Cheddar
							|| ingredient instanceof Patties) {
						temp.push(new Onions());
						temp.push(ingredient);
						break;
					}
					temp.push(ingredient);
				}

			} else if (category instanceof Pepperjack) {

				int pattyNum = this.numberPatty;
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (pattyNum == 1) {
						if (ingredient instanceof Pepperjack
								|| ingredient instanceof Mozzarella
								|| ingredient instanceof Cheddar
								|| ingredient instanceof Patties) {
							temp.push(new Pepperjack());
							temp.push(ingredient);
							break;
						}
					}
					if (ingredient instanceof Patties)
						pattyNum--;
					temp.push(ingredient);
				}

			} else if (category instanceof Mozzarella) {

				int pattyNum = this.numberPatty;
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (pattyNum == 1) {
						if (ingredient instanceof Mozzarella
								|| ingredient instanceof Cheddar
								|| ingredient instanceof Patties) {
							temp.push(new Mozzarella());
							temp.push(ingredient);
							break;
						}
					}
					if (ingredient instanceof Patties)
						pattyNum--;
					temp.push(ingredient);
				}

			} else if (category instanceof Cheddar) {

				int pattyNum = this.numberPatty;
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if (pattyNum == 1) {
						if (ingredient instanceof Cheddar
								|| ingredient instanceof Patties) {
							temp.push(new Cheddar());
							temp.push(ingredient);
							break;
						}
					}
					if (ingredient instanceof Patties)
						pattyNum--;
					temp.push(ingredient);
				}

			} else if (category instanceof Patties) {

				addPatty();

			} else if (category instanceof Mushrooms) {

				int numBun = 2;
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if ((ingredient instanceof Bun) && (numBun > 1))
						numBun--;
					else {
						if ((numBun < 2)
								&& (ingredient instanceof Mushrooms
										|| ingredient instanceof Mustard
										|| ingredient instanceof Ketchup || ingredient instanceof Bun)) {
							temp.push(new Mushrooms());
							temp.push(ingredient);
							break;
						}
					}
					temp.push(ingredient);
				}

			} else if (category instanceof Mustard) {

				int numBun = 2;
				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if ((ingredient instanceof Bun) && (numBun > 1))
						numBun--;
					else {
						if ((numBun < 2)
								&& (ingredient instanceof Mustard
										|| ingredient instanceof Ketchup || ingredient instanceof Bun)) {
							temp.push(new Mustard());
							temp.push(ingredient);
							break;
						}
					}
					temp.push(ingredient);
				}

			} else if (category instanceof Ketchup) {
				int numBun = 2;

				while (!burger.isEmpty()) {
					Object ingredient = burger.pop();
					if ((ingredient instanceof Bun) && (numBun > 1))
						numBun--;
					else {
						if ((numBun < 2)
								&& (ingredient instanceof Ketchup || ingredient instanceof Bun)) {
							temp.push(new Ketchup());
							temp.push(ingredient);
							break;
						}
					}
					temp.push(ingredient);
				}

			}

			while (!temp.isEmpty())
				burger.push(temp.pop());

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method removes the ingredient type from the Burger.
	 * 
	 * @param type
	 */
	public void removeIngredient(String type) {
		try {
			MyStack<Object> temp = new MyStack<Object>();
			Object category = Class.forName(Burger.classPrefix + type)
					.newInstance();

			if (category instanceof Bun)
				return;

			if (category instanceof Patties) {
				removePatty();
				return;
			}

			while (!burger.isEmpty()) {
				Object ingredient = burger.pop();
				if (ingredient.getClass().equals(category.getClass())) {
					continue;
				}
				temp.push(ingredient);
			}
			while (!temp.isEmpty())
				burger.push(temp.pop());

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class Bun {
}

class Patties {
}

class Cheese {
}

class Veggies {
}

class Sauces {
}
class Beef extends Patties {
}

class Chicken extends Patties {
}

class Veggie extends Patties {
}

class Cheddar extends Cheese {
}

class Mozzarella extends Cheese {
}

class Pepperjack extends Cheese {
}

class Lettuce extends Veggies {
}

class Tomato extends Veggies {
}

class Onions extends Veggies {
}

class Pickle extends Veggies {
}

class Mushrooms extends Veggies {
}

class Ketchup extends Sauces {
}

class Mustard extends Sauces {
}

class Mayonnaise extends Sauces {
}

class BaronSauce extends Sauces {
}