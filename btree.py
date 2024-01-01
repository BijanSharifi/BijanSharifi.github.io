from __future__ import annotations
import json
from typing import List
import math
from bisect import insort

# Node Class.
# You may make minor modifications.
class Node():
    def  __init__(self,
                  keys     : List[int]  = None,
                  values   : List[str] = None,
                  children : List[Node] = None,
                  parent   : Node = None):
        self.keys     = keys
        self.values   = values
        self.children = children
        self.parent   = parent

# DO NOT MODIFY THIS CLASS DEFINITION.
class Btree():
    def  __init__(self,
                  m    : int  = None,
                  root : Node = None):
        self.m    = m
        self.root = root

    # DO NOT MODIFY THIS CLASS METHOD.
    def dump(self) -> str:
        def _to_dict(node) -> dict:
            return {
                "keys": node.keys,
                "values": node.values,
                "children": [(_to_dict(child) if child is not None else None) for child in node.children]
            }
        if self.root == None:
            dict_repr = {}
        else:
            dict_repr = _to_dict(self.root)
        return json.dumps(dict_repr,indent=2)
        
           
   
   
    def find_median(self, data):
        sorted_data = sorted(data)  
        n = len(sorted_data)
    
        if n % 2 == 1:  
            median = sorted_data[n // 2]
            median_index = data.index(median)  
        else:  
            median_index = n // 2 - 1  
    
        return median_index
    
    
   
        


    # Delete.
    def delete(self, key: int):
        n = self.deleteAux(self.root, key)
        if n == self.root:
            return
        else:
            if len(n.keys) < math.ceil(self.m/2)-1:
                self.merge(n)


    
    def deleteAux(self, n : Node, key : int) -> Node:

        if find_index(n.keys, key) is not None:
            if n.children[0] is None:
                index = n.keys.index(key)
                n.keys.remove(key)
                n.children.pop()
                del n.values[index]
                return n
            else:
                #find inorder successor and put it and the value here. then, call deleteAux again
                index = n.keys.index(key)
                inordkey, inordval = minKey(n.children[index+1])
                n.keys[index] = inordkey
                n.values[index] = inordval
                return self.deleteAux(n.children[index+1], inordkey)
                
        else:
            i=0
            for keyz in n.keys:
                if keyz < key:
                    i += 1
                else:
                    break
            return self.deleteAux(n.children[i], key)
    

    def insertAux(self, n : Node, key : int, value : str)-> Node:
        if n.children[0] is None:
            insort(n.keys, key)
            index = n.keys.index(key)
            n.values.insert(index, value)
            n.children.append(None)
            return n
        else:
            i = 0
            for keyz in n.keys:
                if keyz < key:
                    i += 1
                else:
                    break
            return self.insertAux(n.children[i], key, value)
    
    def insert(self, key : int, value : str):
        if self.root is None:
            self.root = Node(keys=[key], values=[value], children=[None]*2, parent=None)
        else:
            n = self.insertAux(self.root, key, value)
            if len(n.keys) > self.m-1:
                self.split(n)
            
    def split(self, n : Node):
        if len(n.keys) < self.m:
            return 
        if n.parent is not None:
            rightSib, leftSib, parentI = self.leftRightSib(n)
            if leftSib is not None and len(leftSib.keys) < self.m-1:
                t = len(leftSib.keys) + len(n.keys)
                while len(n.keys) > math.ceil(t/2):
                    self.rotateLeft2(n, leftSib, parentI-1)
            elif rightSib is not None and len(rightSib.keys) < self.m-1:
                t = len(rightSib.keys) + len(n.keys)
                while len(n.keys) > math.ceil(t/2):
                    self.rotateRight2(n, rightSib, parentI)
            else:
                medianI = self.find_median(n.keys)
                firstHalf = n.keys[:medianI]
                median = n.keys[medianI]
                secondHalf = n.keys[medianI +1:]
                firstHalfVal = n.values[:medianI]
                medianVal = n.values[medianI]
                secondHalfVal = n.values[medianI +1:]
                if n.children[0] is None:
                    firstHalfChildren = [None]*(len(firstHalf)+1)
                    secondHalfChildren = [None] * (len(secondHalf)+1)
                else:
                    firstHalfChildren = n.children[:medianI +1]
                    secondHalfChildren = n.children[medianI+1:]
                leftNode = Node(keys=firstHalf, values=firstHalfVal, children=firstHalfChildren, parent=None)
                rightNode = Node(keys=secondHalf, values=secondHalfVal, children=secondHalfChildren, parent=None)
                if leftNode.children[0] is not None:
                    for child in leftNode.children:
                        child.parent = leftNode
                if rightNode.children[0] is not None:
                    for child in rightNode.children:
                        child.parent = rightNode
                n.parent.keys.insert(parentI, median)
                n.parent.values.insert(parentI, medianVal)
                n.parent.children[parentI]=leftNode
                leftNode.parent = n.parent
                n.parent.children.insert(parentI+1, rightNode)
                rightNode.parent = n.parent
                self.split(n.parent)
        else:
            medianI = self.find_median(n.keys)
            firstHalf = n.keys[:medianI]
            median = n.keys[medianI]
            secondHalf = n.keys[medianI +1:]
            firstHalfVal = n.values[:medianI]
            medianVal = n.values[medianI]
            secondHalfVal = n.values[medianI +1:]
            if n.children[0] is None:
                firstHalfChildren = [None]*(len(firstHalf)+1)
                secondHalfChildren = [None] * (len(secondHalf)+1)
            else:
                firstHalfChildren = n.children[:medianI +1]
                secondHalfChildren = n.children[medianI+1:]
            leftNode = Node(keys=firstHalf, values=firstHalfVal, children=firstHalfChildren, parent=None)
            rightNode = Node(keys=secondHalf, values=secondHalfVal, children=secondHalfChildren, parent=None)
            if leftNode.children[0] is not None:
                for child in leftNode.children:
                    child.parent = leftNode
            if rightNode.children[0] is not None:
                for child in rightNode.children:
                    child.parent = rightNode
            parentNode = Node(keys=[median], values=[medianVal], children=[leftNode, rightNode], parent=None)
            leftNode.parent = parentNode
            rightNode.parent = parentNode
            self.root = parentNode
            




        



        



    def leftRightSib(self, n : Node)-> tuple(Node, Node, int):
        parent = n.parent
        index = parent.children.index(n)
        if index+1 > len(parent.children) -1:
            rightSib = None
        else:
            rightSib = parent.children[index+1]
        if index -1 <0:
            leftSib = None
        else:
            leftSib = parent.children[index-1]
        return rightSib, leftSib, index
        

    
    def merge(self, n : Node):
        if n == self.root:
            return
        if len(n.keys) < math.ceil(self.m/2) -1:
            rightSib, leftSib, parentI = self.leftRightSib(n)
            if leftSib is not None and len(leftSib.keys) > math.ceil(self.m/2)-1:
                t = len(leftSib.keys) + len(n.keys)
                while len(n.keys) < math.floor(t/2):
                    self.rotateRight2(leftSib, n, parentI-1)
            elif rightSib is not None and len(rightSib.keys) > math.ceil(self.m/2)-1:
                t = len(rightSib.keys) + len(n.keys)
                while len(n.keys) < math.floor(t/2):
                    self.rotateLeft2(rightSib, n, parentI)
            elif leftSib is not None:
                numParentKeys = len(n.parent.keys)
                if numParentKeys == 1:
                   newChildKey = n.parent.keys.pop()
                   newChildValue = n.parent.values.pop()
                   newNodeKeys = leftSib.keys + [newChildKey] + n.keys
                   newNodeValues = leftSib.values + [newChildValue] + n.values
                   newNodeChildren = leftSib.children + n.children
                   newNode = Node(keys=newNodeKeys, values=newNodeValues, children=newNodeChildren, parent=None)
                   if newNode.children[0] is not None:
                       for child in newNode.children:
                           child.parent = newNode
                   if n.parent.parent is None:
                       self.root=newNode
                   else:
                       newNode.parent = n.parent
                       n.parent.children = [newNode]
                       self.merge(newNode.parent)
                       
                    
                        
                else:
                    parent = n.parent
                    newChildKey = parent.keys.pop(parentI-1)
                    newChildValue = parent.values.pop(parentI-1)
                    newNodeKeys = leftSib.keys + [newChildKey] + n.keys
                    newNodeValues = leftSib.values + [newChildValue] + n.values
                    newNodeChildren = leftSib.children + n.children
                    newNode = Node(keys=newNodeKeys, values=newNodeValues, children=newNodeChildren, parent = parent)

                    if newNode.children[0] is not None:
                        for child in newNode.children:
                            child.parent = newNode
                    
                    parent.children.pop(parentI)
                    parent.children.pop(parentI-1)
                    parent.children.insert(parentI-1, newNode)

                    self.merge(parent)
            else:
                numParentKeys = len(n.parent.keys)
                if numParentKeys == 1:
                    newChildKey = n.parent.keys.pop()
                    newChildValue = n.parent.values.pop()
                    newNodeKeys = n.keys + [newChildKey] + rightSib.keys
                    newNodeValues = n.values + [newChildValue] + rightSib.values
                    newNodeChildren = n.children + rightSib.children
                    newNode = Node(keys=newNodeKeys, values=newNodeValues, children=newNodeChildren, parent=None)
                    if newNode.children[0] is not None:
                        for child in newNode.children:
                            child.parent = newNode
                    if n.parent.parent is None:
                        self.root=newNode
                    else:
                        newNode.parent = n.parent
                        n.parent.children = [newNode]
                        self.merge(newNode.parent)

                else:
                    parent = n.parent
                    newChildKey = parent.keys.pop(parentI)
                    newChildValue = parent.values.pop(parentI)
                    newNodeKeys = n.keys + [newChildKey] + rightSib.keys
                    newNodeValues = n.values + [newChildValue] + rightSib.values
                    newNodeChildren = n.children + rightSib.children
                    newNode = Node(keys=newNodeKeys, values=newNodeValues, children=newNodeChildren, parent = parent)

                    if newNode.children[0] is not None:
                        for child in newNode.children:
                            child.parent = newNode

                    parent.children.pop(parentI + 1)
                    parent.children.pop(parentI)
                    parent.children.insert(parentI, newNode)

                    self.merge(parent)


                    
                    
                    

                    



                



    #need to move children over as well 
    def rotateRight2(self, n : Node, rightNode : Node, parentI : int):
        newParent = n.keys.pop()  
        newParentVal = n.values.pop() 
        newRightKey = n.parent.keys[parentI]    
        newRightValue = n.parent.values[parentI] 
        n.parent.keys[parentI] = newParent  
        n.parent.values[parentI] = newParentVal
        rightNode.keys.insert(0, newRightKey) 
        rightNode.values.insert(0, newRightValue)
        if n.children[0] is not None:
            rightChild = n.children.pop()
            rightNode.children.insert(0, rightChild)
            rightChild.parent = rightNode
        else:
            n.children.pop()
            rightNode.children.append(None)


    def rotateLeft2(self, n : Node, leftNode : Node, parentI : int):
        newParent = n.keys.pop(0)
        newParentVal = n.values.pop(0)
        newLeftKey = n.parent.keys[parentI]
        newLeftValue = n.parent.values[parentI]
        n.parent.keys[parentI] = newParent
        n.parent.values[parentI] = newParentVal
        leftNode.keys.append(newLeftKey)
        leftNode.values.append(newLeftValue)
        if n.children[0] is not None:
            leftChild = n.children.pop(0)
            leftNode.children.append(leftChild)
            leftChild.parent = leftNode
        else:
            n.children.pop()
            leftNode.children.append(None)


                

   # Search
    def search(self,key) -> str:
        lst = []
        t = self.root
        while find_index(t.keys, key) is None:
            i = 0
            numChildren = len(t.children)
            while i < numChildren-1:
                if key<t.keys[i]:
                    lst.append(i)
                    t = t.children[i]
                    break
                else:
                    i += 1
                    if i>= numChildren-1:
                        lst.append(i)
                        t = t.children[i]
                        break
        index = find_index(t.keys, key)
        value = t.values[index]
        lst.append(value)
        
        return json.dumps(lst)

def find_index(lst, number):
    for index, item in enumerate(lst):
        if item == number:
            return index
    return None

def minKey(n: Node) -> tuple(int, str):
        currNode = n
        if currNode.children[0] is None:
            return (currNode.keys[0], currNode.values[0])
        else:
            return minKey(currNode.children[0])