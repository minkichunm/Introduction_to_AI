# -*- coding: utf-8 -*-
"""
Created on Mon Mar  8 20:26:27 2021

@author: treha
"""


# maybe changing the color
import numpy as np
from Board import Board
import random

class AI:

    def __init__(self, board):
        self.board = board
        self.listOfCoordinates = self.getAllPlayer2()
        #first coordinates are always 0 0
        self.listOfDestinationCoordinates = "NONE"



    def updateBoard(self, board):
        self.board = board
        self.listOfCoordinates = self.getAllPlayer2()



    def getAllPlayer2(self):

        result = np.where(self.board.board == 2)



        listOfCoordinates= np.array(list(zip(result[0], result[1])))
        #print(listOfCoordinates)
        return listOfCoordinates


    def makeTheMove(self, board):


        self.board = board
        self.listOfCoordinates = self.getAllPlayer2()
        print(len(self.listOfCoordinates))
        self.listOfDestinationCoordinates = "NONE"
        while (self.listOfDestinationCoordinates == "NONE" or len(self.listOfDestinationCoordinates) == 1):
            rand1 = random.randint(0, len(self.listOfCoordinates)-1 )
            x_old = self.listOfCoordinates[rand1][0]
            y_old = self.listOfCoordinates[rand1][1]


            self.validMoveWalk(x_old, y_old)
            board.resetAfterMoves()

            print(self.listOfDestinationCoordinates)
        rand2 = random.randint(1, len(self.listOfDestinationCoordinates)-1 )

        x_new  = (self.listOfDestinationCoordinates[rand2][0])

        y_new = (self.listOfDestinationCoordinates[rand2][1])



        board.setEntry(x_new, y_new, board.currentPlayer)
        board.setEntry(x_old, y_old, 0)



        board.currentPlayerIncrement()
        if board.checkWin():
            print("WIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")




    def validMoveWalk(self,  x, y):
        self.listOfDestinationCoordinates = np.array([[0,0]])
        try:
            if self.board.getEntry(x+1,y+1) == 0:
                self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates, np.array([[x+1,y+1]])), axis=0)
        except:
            pass

        try:
            if self.board.getEntry(x-1,y-1) == 0:
                self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates,  np.array([[x-1,y-1]])), axis=0)

        except:
            pass
        try:
            if self.board.getEntry(x+1,y-1) == 0:
                self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates,  np.array([[x+1,y-1]])), axis=0)

        except:
            pass
        try:

            if self.board.getEntry(x-1,y+1) == 0:
                self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates,  np.array([[x-1,y+1]])), axis=0)

        except:
            pass

        self.validMoveJump(x, y)


    def validMoveJump(self, x, y):
        # some of these calls crush, we might need a try except
        try:

            if self.board.getEntry(x+1, y+1) in range(1, 7):
                if (self.board.getEntry(x+2, y+2) == 0):
                    self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates,  np.array([[x+2,y+2]])), axis=0)
                    self.board.setEntry(x+2, y+2, 12)

                    self.validMoveJump( x+2, y+2)
        except:
            pass

        try:
            if self.board.getEntry(x-1, y-1) in range(1, 7):
                if (self.board.getEntry(x-2, y-2) == 0):
                    self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates,  np.array([[x-2,y-2]])), axis=0)
                    self.board.setEntry(x-2, y-2, 12)

                    self.validMoveJump( x-2, y-2)
        except:
            pass

        try:

            if self.board.getEntry(x+1, y-1) in range(1, 7):
                if (self.board.getEntry(x+2, y-2) == 0):
                    self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates,  np.array([[x+2,y-2]])), axis=0)
                    self.board.setEntry(x+2, y-2, 12)

                    self.validMoveJump( x+2, y-2)

        except:
            pass

        try:

            if self.board.getEntry(x-1, y+1) in range(1, 7):
                if (self.board.getEntry(x-2, y+2) == 0):
                   self.listOfDestinationCoordinates = np.concatenate((self.listOfDestinationCoordinates,  np.array([[x-2,y+2]])), axis=0)
                   self.board.setEntry(x-2, y+2, 12)

                   self.validMoveJump( x-2, y+2)

        except:

            pass

    def greedyMove(self):
        pass

    def computeheuristics():
        pass
