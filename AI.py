# -*- coding: utf-8 -*-
"""
Created on Mon Mar  8 20:26:27 2021

@author: treha
"""

## validMoveWalk/validMoveJump !!!!!


# maybe changing the color
import numpy as np
from Board import Board
import random
import math
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


    def makeRandomMove(self, board):


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

    def makeGreedyMove(self, board):

        self.board = board
        self.listOfCoordinates = self.getAllPlayer2()
        (x_new,x_old, y_old, y_new) = self.greedyMoveCoordinates()

        board.setEntry(x_new, y_new, board.currentPlayer)
        board.setEntry(x_old, y_old, 0)

        board.resetAfterMoves()


        board.currentPlayerIncrement()
        if board.checkWin():
            print("WIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")





    def validMoveWalk(self,  x, y):
        self.listOfDestinationCoordinates = np.array([[99,99]])
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

    def greedyMoveCoordinates(self):
        best_heuristics = 100000.0
        best_xnew = 0
        best_xold = 0
        best_yold = 0
        best_ynew = 0

        for i in range(10):
            self.validMoveWalk(self.listOfCoordinates[i][0],self.listOfCoordinates[i][1])
            x_torestore = self.listOfCoordinates[i][0]
            y_torestore = self.listOfCoordinates[i][1]

            for j in range(len(self.listOfDestinationCoordinates)):
                self.listOfCoordinates[i][0] = self.listOfDestinationCoordinates[j][0]
                self.listOfCoordinates[i][1] = self.listOfDestinationCoordinates[j][1]
                if best_heuristics > self.computeheuristics():
                    best_heuristics = self.computeheuristics()
                    best_xold = x_torestore
                    best_yold = y_torestore
                    best_xnew = self.listOfDestinationCoordinates[j][0]
                    best_ynew = self.listOfDestinationCoordinates[j][1]

                self.listOfCoordinates[i][0] = x_torestore
                self.listOfCoordinates[i][1] = y_torestore
        print(self.listOfDestinationCoordinates)
        print(best_heuristics)
        return (best_xnew , best_xold , best_yold  ,best_ynew )

    def computeheuristics(self):
        total_distance = 0.0
        for i in range(10):
            distance = math.sqrt(float(self.listOfCoordinates[i][0]-(-10))**2+float(self.listOfCoordinates[i][1]-12)**2)
            total_distance += distance

        return total_distance
