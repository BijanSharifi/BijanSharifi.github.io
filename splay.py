from __future__ import annotations
import json
from typing import List

verbose = False 

# DO NOT MODIFY!
class Node():
    def  __init__(self,
                  key       : int,
                  leftchild  = None,
                  rightchild = None,
                  parent     = None,):
        self.key        = key
        self.leftchild  = leftchild
        self.rightchild = rightchild
        self.parent     = parent

# DO NOT MODIFY!
class SplayTree():
    def  __init__(self,
                  root : Node = None):
        self.root = root

    # For the tree rooted at root:
    # Return the json.dumps of the object with indent=2.
    # DO NOT MODIFY!
    def dump(self) -> str:
        def _to_dict(node) -> dict:
            pk = None
            if node.parent is not None:
                pk = node.parent.key
            return {
                "key": node.key,
                "left": (_to_dict(node.leftchild) if node.leftchild is not None else None),
                "right": (_to_dict(node.rightchild) if node.rightchild is not None else None),
                "parentkey": pk
            }
        if self.root == None:
            dict_repr = {}
        else:
            dict_repr = _to_dict(self.root)
        return json.dumps(dict_repr,indent = 2)

    # Search
    def search(self,key:int):
        self.splay(key, self.root)
        

    # Insert Method 1
    def insert(self,key:int):
        if self.root is None:
            self.root = Node(key=key, leftchild=None, rightchild=None, parent=None)
        else:
            subtree = self.splay(key, self.root)
            newRoot = Node(key=key, leftchild=None, rightchild=None, parent=None)
            self.root=newRoot
            if subtree.key < key:
                newRoot.rightchild = subtree.rightchild
                if subtree.rightchild is not None:
                    subtree.rightchild.parent = newRoot
                subtree.rightchild = None
                newRoot.leftchild = subtree
                subtree.parent = newRoot
            else:
                newRoot.leftchild = subtree.leftchild
                if subtree.leftchild is not None:
                    subtree.leftchild.parent = newRoot
                subtree.leftchild = None
                newRoot.rightchild = subtree
                subtree.parent = newRoot


            
    
    

    def leftRotate(self, x: Node):
        y = x.rightchild
        x.rightchild = y.leftchild
        if y.leftchild is not None:
            y.leftchild.parent = x

        y.parent = x.parent
        if x.parent is None:
            self.root = y
        elif x is x.parent.leftchild:
            x.parent.leftchild = y
        else:
            x.parent.rightchild = y

        y.leftchild = x
        x.parent = y

    def rightRotate(self, x: Node):
        y = x.leftchild
        x.leftchild = y.rightchild
        if y.rightchild is not None:
            y.rightchild.parent = x

        y.parent = x.parent
        if x.parent is None:
            self.root = y
        elif x is x.parent.rightchild:
            x.parent.rightchild = y
        else:
            x.parent.leftchild = y

        y.rightchild = x
        x.parent = y

      
    
    def splay(self, key:int, x:Node)->Node:
        n = self.splayFinder(key, x)


        while n.parent is not None:
            if n.parent.parent is None:
                if n == n.parent.leftchild:
                    self.rightRotate(n.parent)
                else:
                    self.leftRotate(n.parent)
            elif n == n.parent.leftchild and n.parent == n.parent.parent.leftchild:
                self.rightRotate(n.parent.parent)
                self.rightRotate(n.parent)
            elif n == n.parent.rightchild and n.parent == n.parent.parent.rightchild:
                self.leftRotate(n.parent.parent) 
                self.leftRotate(n.parent)
            elif n == n.parent.rightchild and n.parent == n.parent.parent.leftchild:
                self.leftRotate(n.parent)
                self.rightRotate(n.parent)
            else:
                self.rightRotate(n.parent)
                self.leftRotate(n.parent)
        self.root = n
        return n
    
    def splayFinder(self, key:int, n:Node)->Node:
        
        if key<n.key:
            if n.leftchild is None:
                return n
            else:
                return self.splayFinder(key, n.leftchild)
        elif key>n.key:
            if n.rightchild is None:
                return n
            else:
                return self.splayFinder(key, n.rightchild)
        else:
            return n
    
  

    def delete(self, key:int):
        self.splay(key, self.root)
        if self.root.key == key:
            root = self.root
            if root.leftchild is None:
                root.rightchild.parent = None
                self.root = root.rightchild
            elif root.rightchild is None:
                root.leftchild.parent = None
                self.root = root.leftchild
            else:
                self.splay(key, root.rightchild)
                newRoot = self.root
                newRoot.leftchild = root.leftchild
                newRoot.leftchild.parent = newRoot
                self.root = newRoot


  
